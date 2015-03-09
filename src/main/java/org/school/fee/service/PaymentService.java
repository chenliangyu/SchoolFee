package org.school.fee.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Payment;

public interface PaymentService {
	public List<Payment> getPayment(ObjectId studentId);
	public void pay(ObjectId paymentId,double money);
}
