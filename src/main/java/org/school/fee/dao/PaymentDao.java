package org.school.fee.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentDao {
	public Page<Payment> findPayment(Pageable page,ObjectId studentId,String studentName,
			String klass,String school,ObjectId feeId,String feeName,Boolean notClear,
			Date startDate,Date endDate); 
	public List<Payment> findNotClearPaymentByExpireDate(Date expireDate);
}
