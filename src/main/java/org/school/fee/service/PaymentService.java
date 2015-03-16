package org.school.fee.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.PayResult;
import org.school.fee.models.Payment;
import org.springframework.data.domain.Page;

public interface PaymentService {
	public void addPayment(Payment payment);
	public void savePayment(Payment payment);
	public void deletePayment(ObjectId id);
	public void deletePayment(ObjectId[] ids);
	public Payment getPayment(ObjectId id);
	
	public void pay(Payment payment,BigDecimal payMoney);
	
	public Page<Payment> listPayment(Integer page,Integer pageSize,String studentName,
			String feeName,String klass,String school,Boolean notClear,Date startDate,
			Date endDate,String orderBy,String order);
	public Page<Payment> listPaymentFromStudent(Integer page,Integer pageSize,ObjectId studentId,
			String feeName,Boolean notClear,Date startDate,Date endDate,
			String orderBy,String order);
	public Page<Payment> listPaymentFromFee(Integer page,Integer pageSize,ObjectId feeId,
			String studentName,	String klass,String school,Boolean notClear,
			Date startDate,Date endDate,String orderBy,String order);
	public List<Payment> findNotClearPaymentByDate(Date date);
}
