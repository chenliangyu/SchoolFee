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
		String msg = prop.getProperty("msg");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String expireDate = sdf.format(payment.getExpireDate());
		StringBuilder payRecord = new StringBuilder();
		List<PayRecord> pays = payment.getMoney();
		BigDecimal payTotal = new BigDecimal(0);
		for(PayRecord singlePay:pays){
			String singlePayDate = sdf.format(singlePay.getPayDate());
			String singlePayMoney = singlePay.getMoney().setScale(1, RoundingMode.HALF_UP).toString();
			payRecord.append("<span class='single_pay'><span class='font_red'>"+singlePayDate+"</font_red>缴费<span class='font_red'>"+singlePayMoney+"</span>元</span>");
			payTotal.add(singlePay.getMoney());
		}
		BigDecimal rest = getRestMoney(payment);
		String showRest = rest.setScale(1,RoundingMode.HALF_UP).toString();
		String showTotal = payment.getFeeMoney().setScale(1, RoundingMode.HALF_UP).toString();
		String showPayTotal = payTotal.setScale(1, RoundingMode.HALF_UP).toString();
		String notify = MessageFormat.format(msg, student.getName(),payment.getFeeName(),expireDate,showTotal,showPayTotal,payRecord,showRest);
		Message message = new Message();
		message.setMsgContent(notify);
		message.setUserId(userService.findAdmin().getId());
		saveMessage(message);
	}
	private String generateOnePayNotice(String template,Student student,Payment payment){
		PayResult payResult = payment.getPayResults().get(0);
		if(!payResult.getHasNotified()){
			payResult.setHasNotified(true);
			paymentService.savePayment(payment);
			String studentName = student.getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String expireDate = sdf.format(payResult.getExpireDate());
			String total = payment.getFeeMoney().setScale(1, RoundingMode.HALF_UP).toString();
			BigDecimal payTotal = payResult.getMoney();
			String rest = payResult.getRestMoney().setScale(1, RoundingMode.HALF_UP).toString();
			String totalToShow = payTotal.setScale(0, RoundingMode.HALF_UP).toString();
			StringBuilder payRecord = new StringBuilder();
			List<PayRecord> pays = payment.getMoney();
			for(PayRecord singlePay:pays){
				String singlePayDate = sdf.format(singlePay.getPayDate());
				String singlePayMoney = singlePay.getMoney().setScale(1, RoundingMode.HALF_UP).toString();
				payRecord.append("<span class='single_pay'><span class='font_red'>"+singlePayDate+"</font_red>缴费<span class='font_red'>"+singlePayMoney+"</span>元</span>");
			}
			return MessageFormat.format(template,studentName,payment.getFeeName(),expireDate,total,totalToShow,payRecord,rest);
		}
		return null;
	}
	private String generateInstalmentNotice(String template,Student student,Payment payment){
		PayResult payResult = payment.getPayResults().get(payment.getPayResults().size() - 1);
		Date lastSMSSendDate = payResult.getLastSMSSendDate();
		DateTime today = DateTime.now().hourOfDay().setCopy(0).minuteOfHour().setCopy(0).secondOfMinute().setCopy(0);
		if(lastSMSSendDate!=null){
			DateTime lssd = new DateTime(lastSMSSendDate);
			Boolean shouldSend = true;
			switch(SMSPeriod.values()[payment.getSmsPeriod()]){
				case day:
				case week:
				case month:
			}
		}
		if(!payResult.getHasNotified()){
			payResult.setHasNotified(true);
			paymentService.savePayment(payment);
			String studentName = student.getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String expireDate = sdf.format(payResult.getExpireDate());
			String total = payment.getFeeMoney().setScale(1, RoundingMode.HALF_UP).toString();
			BigDecimal payTotal = payResult.getMoney();
			String rest = payResult.getRestMoney().setScale(1, RoundingMode.HALF_UP).toString();
			String totalToShow = payTotal.setScale(0, RoundingMode.HALF_UP).toString();
			StringBuilder payRecord = new StringBuilder();
			List<PayRecord> pays = payment.getMoney();
			for(PayRecord singlePay:pays){
				String singlePayDate = sdf.format(singlePay.getPayDate());
				String singlePayMoney = singlePay.getMoney().setScale(1, RoundingMode.HALF_UP).toString();
				payRecord.append("<span class='single_pay'><span class='font_red'>"+singlePayDate+"</font_red>缴费<span class='font_red'>"+singlePayMoney+"</span>元</span>");
			}
			return MessageFormat.format(template,studentName,payment.getFeeName(),expireDate,total,totalToShow,payRecord,rest);
		}
		
		return null;
	}
	
	public void saveMessage(Message message){
		messageRepository.insert(message);
	}
	public void sendSMS(Student student,Payment payment) {
		String studentName = student.getName();
		String showTotal = payment.getFeeMoney().setScale(1, RoundingMode.HALF_UP).toString();
		BigDecimal rest = getRestMoney(payment);
		String showRest = rest.setScale(1,RoundingMode.HALF_UP).toString();
		String template = getSMSTemplate();
		String smsContent = MessageFormat.format(template, studentName,payment.getFeeName(),showTotal,showRest);
		String phones = student.getPhone();
		String[] phoneArray = phones.split("/ /");
		List<String> phoneList = Arrays.asList(phoneArray);
		SMSUtils.sendSMS(phoneList, smsContent);
		// TODO Auto-generated method stub
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
