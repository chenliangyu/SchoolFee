package org.school.fee.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, ObjectId>{
	public List<Payment> findByStudentId(ObjectId studentId);
}
