package org.school.fee.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.school.fee.dao.PaymentDao;
import org.school.fee.models.PayRecord;
import org.school.fee.models.PayResult;
import org.school.fee.models.Payment;
import org.school.fee.repository.PaymentRepository;
import org.school.fee.service.PaymentService;
import org.school.fee.support.enums.PayMethod;
import org.school.fee.support.enums.PayStatus;
import org.school.fee.support.utils.PageUtils;
import org.school.fee.support.utils.PayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PaymentDao paymentDao;

	public void addPayment(Payment payment) {
		// TODO Auto-generated method stub
		PayResult payResult = new PayResult();
		if(payment.getPayMethod()==PayMethod.onePay.ordinal()){
			payResult.setExpireDate(payment.getExpireDate());
			payResult.setStatus(PayStatus.notClear.ordinal());
			payResult.setPayMoney(payment.getFeeMoney());
			payment.getPayResults().add(payResult);
		}else if(payment.getPayMethod() == PayMethod.instalment.ordinal()){
			Date expireDate = PayUtils.getNextExpireDate(payment);
			payResult.setExpireDate(expireDate);
			payResult.setPayMoney(payment.getInstalmentMoney());
			payResult.setStatus(PayStatus.notClear.ordinal());
			payment.getPayResults().add(payResult);
		}
		paymentRepository.insert(payment);
	}

	public void savePayment(Payment payment) {
		// TODO Auto-generated method stub
		paymentRepository.save(payment);
	}

	public void deletePayment(ObjectId id) {
		// TODO Auto-generated method stub
		paymentRepository.delete(id);
	}

	public void deletePayment(ObjectId[] ids) {
		// TODO Auto-generated method stub
		for(ObjectId id:ids){
			deletePayment(id);
		}
	}

	public Payment getPayment(ObjectId id) {
		// TODO Auto-generated method stub
		return paymentRepository.findOne(id);
	}

	public Page<Payment> listPayment(Integer page, Integer pageSize,
			String studentName, String feeName, String klass, String school,
			Boolean notClear, PayMethod payMethod,Date startDate,
			Date endDate,String orderBy,
			String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, null, studentName, klass, school, null, 
				feeName, notClear, payMethod,startDate, endDate);
	}

	public Page<Payment> listPaymentFromStudent(Integer page, Integer pageSize,
			ObjectId studentId, String feeName, Boolean notClear,
			PayMethod payMethod,Date startDate,
			Date endDate,String orderBy, String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, studentId, null, null, null, null, 
				feeName, notClear, payMethod,startDate, endDate);
	}

	public Page<Payment> listPaymentFromFee(Integer page, Integer pageSize,
			ObjectId feeId, String studentName, String klass, String school,
			Boolean notClear,PayMethod payMethod,Date startDate,
			Date endDate, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, null, studentName, klass, school, feeId, 
				null, notClear,payMethod,startDate, endDate);
	}

	public List<Payment> findNotClearPaymentByDate(Date date) {
		// TODO Auto-generated method stub
		DateTime datetime = new DateTime(date);
		Date startOfToday = datetime.hourOfDay().setCopy(0).minuteOfHour().setCopy(0).secondOfMinute().setCopy(0).toDate();
		return paymentDao.findNotClearPaymentByExpireDate(startOfToday);
	}

	public void pay(Payment payment, BigDecimal payMoney) {
		Date now = new Date();
		PayRecord payRecord = new PayRecord();
		payRecord.setMoney(payMoney);
		payRecord.setPayDate(now);
		payment.getMoney().add(payRecord);
		if(payment.getPayMethod() == PayMethod.onePay.ordinal()){
			BigDecimal total = payment.getPayTotal();
			if(total.compareTo(payment.getFeeMoney())>=0){
				payment.getPayResults().get(0).setStatus(PayStatus.clear.ordinal());
			}
			payment.getPayResults().get(0).setMoney(total);
		}else{
			BigDecimal total = payment.getPayTotal();
			List<PayResult> payResults = payment.getPayResults();
			for(PayResult payResult : payResults){
				if(total.compareTo(payResult.getPayMoney())>=0 && payResult.getStatus()!=PayStatus.clear.ordinal()){
					payResult.setStatus(PayStatus.clear.ordinal());
					payResult.setMoney(payResult.getPayMoney());
				}else if(payResult.getStatus()!=PayStatus.clear.ordinal()){
					payResult.setMoney(total);
				}
				total = total.subtract(payResult.getPayMoney());
			}
			int i = payResults.size();
			while(total.compareTo(BigDecimal.ZERO)>=0 && i<payment.getInstalment()) {
				PayResult payResult = new PayResult();
				payResult.setExpireDate(PayUtils.getNextExpireDate(payment));
				BigDecimal instalmentMoney = payment.getInstalmentMoney();
				payResult.setPayMoney(instalmentMoney);
				if(instalmentMoney.compareTo(total)<=0){
					payResult.setStatus(PayStatus.clear.ordinal());
					payResult.setMoney(instalmentMoney);
				}else{
					payResult.setMoney(total);
					payResult.setStatus(PayStatus.notClear.ordinal());
				}
				payment.getPayResults().add(payResult);
				total = total.subtract(instalmentMoney);
				i++;
			}
		}
		savePayment(payment);
	}
}
