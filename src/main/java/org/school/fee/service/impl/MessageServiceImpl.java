package org.school.fee.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.school.fee.dao.MessageDao;
import org.school.fee.models.Message;
import org.school.fee.models.PayResult;
import org.school.fee.models.Payment;
import org.school.fee.models.PayRecord;
import org.school.fee.models.Student;
import org.school.fee.repository.MessageRepository;
import org.school.fee.service.MessageService;
import org.school.fee.service.PaymentService;
import org.school.fee.service.StudentService;
import org.school.fee.service.UserService;
import org.school.fee.support.enums.PayMethod;
import org.school.fee.support.enums.PayStatus;
import org.school.fee.support.enums.SMSPeriod;
import org.school.fee.support.utils.PageUtils;
import org.school.fee.support.utils.SMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
	private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	StudentService studentService;
	@Autowired
	UserService userService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	MessageDao messageDao;
	
	public void sendMessage(Payment payment) throws IOException {
		// TODO Auto-generated method stub
		//String studentName = payment.getStudentName();
		//double rest = getRestMoney(payment);
		//Date expireDate = payment.getExpireDate();
		ObjectId studentId = payment.getStudentId();
		Student student = studentService.getStudent(studentId);
		if(payment.getSendMessage()){
			sendSMS(student,payment);
		}
		if(payment.getSendNotify()){
			sendNotify(student,payment);
		}
		logger.debug("need to send sms to phoneArray");
		
	}
	private BigDecimal getRestMoney(Payment payment){
		BigDecimal totalMoney = payment.getFeeMoney();
		List<PayRecord> moneys = payment.getMoney();
		BigDecimal payTotal = new BigDecimal(0);
		for(PayRecord singlePay : moneys){
			payTotal = payTotal.add(singlePay.getMoney());
		}
		BigDecimal restMoney = totalMoney.subtract(payTotal);
		return restMoney;
	}
	public String getSMSTemplate() {
		// TODO Auto-generated method stub
		File file = new File("c:\\sms_template.txt");
		if(file.exists()){
			 BufferedReader reader = null;
		        try {
		            reader = new BufferedReader(new FileReader(file));
		            StringBuilder info = new StringBuilder();
		            String tempString = null;
		            while ((tempString = reader.readLine()) != null) {
		            	info.append(tempString + "\r");
		            }
		            reader.close();
		            return info.toString();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            if (reader != null) {
		                try {
		                    reader.close();
		                } catch (IOException e1) {
		                }
		            }
		        }
		}else{
			logger.debug("file is not exists,create file first");
		}
		return "";
	}
	public void sendNotify(Student student,Payment payment) throws IOException {
		// TODO Auto-generated method stub
		InputStream in = this.getClass().getResourceAsStream("/msg.properties");
		Properties prop = new Properties();
		prop.load(in);
		String template = prop.getProperty("msg");
		PayResult payResult = null;
		if(payment.getPayMethod() == PayMethod.onePay.ordinal()){
			payResult = payment.getPayResults().get(0);
		}else{
			payResult = payment.getPayResults().get(payment.getPayResults().size() - 1);
		}
		if(!payResult.getHasNotified()){
			payResult.setHasNotified(true);
			paymentService.savePayment(payment);
			String studentName = student.getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String expireDate = sdf.format(payResult.getExpireDate());
			String total = payResult.getPayMoney().setScale(1, RoundingMode.HALF_UP).toString();
			BigDecimal payTotal = payResult.getMoney();
			String rest = payResult.getRestMoney().setScale(1, RoundingMode.HALF_UP).toString();
			String totalToShow = payTotal.setScale(0, RoundingMode.HALF_UP).toString();
			StringBuilder payRecord = new StringBuilder();
			List<PayRecord> pays = payment.getMoney();
			if(pays.size()>0){
				payRecord.append("<div class='pay_records'>缴费记录：<ul>");
				for(PayRecord singlePay:pays){
					String singlePayDate = sdf.format(singlePay.getPayDate());
					String singlePayMoney = singlePay.getMoney().setScale(1, RoundingMode.HALF_UP).toString();
					payRecord.append("<li><span class='font_red'>"+singlePayDate+"</font_red>缴费<span class='font_red'>"+singlePayMoney+"</span>元</li>");
				}
				payRecord.append("</ul></div>");
			}
			String feeName = payment.getPayMethod() == PayMethod.onePay.ordinal()?payment.getFeeName() : "第" + payment.getPayResults().size()+"期的"+payment.getFeeName();
			String notify = MessageFormat.format(template,studentName,feeName,expireDate,total,totalToShow,payRecord,rest,student.getId());
			logger.debug("send a notify which is :{}",notify);
			Message message = new Message();
			message.setMsgContent(notify);
			message.setUserId(userService.findAdmin().getId());
			saveMessage(message);
		}
		
	}
	public void saveMessage(Message message){
		messageRepository.insert(message);
	}
	public void sendSMS(Student student,Payment payment) {
		String template = getSMSTemplate();
		String smsContent;
		if(payment.getPayMethod() == PayMethod.onePay.ordinal()){
			smsContent = sendOnepaySMS(template, student, payment);
		}else{
			smsContent = sendInstalmentSMS(template, student, payment);
		}
		if(smsContent == null)return;
		String phones = student.getPhone();
		String[] phoneArray = phones.split("/ /");
		List<String> phoneList = Arrays.asList(phoneArray);
		SMSUtils.sendSMS(phoneList, smsContent);
		// TODO Auto-generated method stub
	}
	private String sendOnepaySMS(String template,Student student,Payment payment){
		PayResult payResult = payment.getPayResults().get(0);
		Date nextSendDate = getNextSendDate(payResult, payment.getSmsInterval(), payment.getSmsPeriod());
		Date today = getToday();
		if(today.before(nextSendDate)){
			return null;
		}
		payResult.setLastSMSSendDate(today);
		String studentName = student.getName();
		String showTotal = payResult.getPayMoney().setScale(1, RoundingMode.HALF_UP).toString();
		BigDecimal rest = payResult.getRestMoney();
		String showRest = rest.setScale(1,RoundingMode.HALF_UP).toString();
		String feeName = payment.getFeeName();
		return MessageFormat.format(template, studentName,feeName,showTotal,showRest);
	}
	private String sendInstalmentSMS(String template,Student student,Payment payment){
		List<PayResult> payResults = payment.getPayResults();
		PayResult payResult = payResults.get(payResults.size() - 1);
		
		Date nextSendDate = getNextSendDate(payResult, payment.getSmsInterval(), payment.getSmsPeriod());
		Date today = getToday();
		if(nextSendDate!=null && today.before(nextSendDate)){
			return null;
		}
		StringBuilder feeName = new StringBuilder();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal rest = BigDecimal.ZERO;
		feeName.append("第");
		int i = 1;
		for(PayResult result : payResults){
			result.setLastSMSSendDate(today);
			if(result.getStatus() == PayStatus.notClear.ordinal()){
				feeName.append(i);
				if(i != payResults.size()){
					feeName.append("、");
				}
				total = total.add(result.getPayMoney());
				rest = rest.add(result.getRestMoney());
			}
			i++;
		}
		feeName.append("期的");
		feeName.append(payment.getFeeName());
		String studentName = student.getName();
		String showTotal = total.setScale(1, RoundingMode.HALF_UP).toString();
		String showRest = rest.setScale(1,RoundingMode.HALF_UP).toString();
		return MessageFormat.format(template, studentName,feeName,showTotal,showRest);
	}
	private Date getToday(){
		return DateTime.now().hourOfDay().setCopy(0).minuteOfHour().setCopy(0).secondOfMinute().setCopy(0).toDate();
	}
	private Date getNextSendDate(PayResult payResult,int interval,int period){
		Date lastSendDate = payResult.getLastSMSSendDate();
		if(lastSendDate == null)return null;
		DateTime lsd = new DateTime(lastSendDate);
		switch(SMSPeriod.values()[period]){
			case week:
				return lsd.plusWeeks(interval).toDate();
			case month:
				return lsd.plusMonths(interval).toDate();
			default:
				return lsd.plusDays(interval).toDate();
		}
	}
	public Page<Message> listMessage(Integer page, Integer pageSize,
			ObjectId userId) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, "createDate", "desc");
		return messageRepository.findByUserId(userId, pageable);
	}
	public void deleteMessage(ObjectId id) {
		// TODO Auto-generated method stub
		messageRepository.delete(id);
	}
	public void deleteMessage(ObjectId[] ids) {
		// TODO Auto-generated method stub
		for(ObjectId id:ids){
			deleteMessage(id);
		}
	}
	public long countNewMessage(ObjectId userId) {
		// TODO Auto-generated method stub
		return messageDao.countNewMessage(userId);
	}
	public void updateAllNewMessage(ObjectId userId) {
		// TODO Auto-generated method stub
		messageDao.updateAllNewMessage(userId);
	}
}
