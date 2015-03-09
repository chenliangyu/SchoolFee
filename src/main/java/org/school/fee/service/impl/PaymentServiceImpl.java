package org.school.fee.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Payment;
import org.school.fee.repository.PaymentRepository;
import org.school.fee.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentRepository paymentRepository;
	
	public List<Payment> getPayment(ObjectId studentId) {
		// TODO Auto-generated method stub
		 return paymentRepository.findByStudentId(studentId);
	}

	public void pay(ObjectId paymentId, double money) {
		// TODO Auto-generated method stub
		Payment payment = paymentRepository.findOne(paymentId);
	}

}
