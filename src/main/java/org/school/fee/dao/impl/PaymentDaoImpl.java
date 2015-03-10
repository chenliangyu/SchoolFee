package org.school.fee.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.dao.PaymentDao;
import org.school.fee.models.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public class PaymentDaoImpl implements PaymentDao {
	
	@Autowired
	MongoTemplate mongoTemplate;

	public Page<Payment> findPayment(Pageable page, ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		Query query = buildQuery(studentId, studentName, klass, school, 
				feeId, feeName, notClear, startDate, endDate);
		query.skip(page.getOffset());
		query.limit(page.getPageSize());
		query.with(page.getSort());
		long total = countPayment(studentId, studentName, klass, school, feeId,
				feeName, notClear, startDate, endDate);
		List<Payment> payments = mongoTemplate.find(query, Payment.class);
		return new PageImpl<Payment>(payments, page, total);
	}
	
	private Query buildQuery(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear, Date startDate, Date endDate){
		Query query = new Query();
		return query;
	}
	
	public long countPayment( ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear, Date startDate, Date endDate){
		Query query  = buildQuery(studentId, studentName, klass,
				school, feeId, feeName, notClear, startDate, endDate);
		return mongoTemplate.count(query, Payment.class);
	}
}
