package org.school.fee.service.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.dao.PaymentDao;
import org.school.fee.models.Payment;
import org.school.fee.repository.PaymentRepository;
import org.school.fee.service.PaymentService;
import org.school.fee.support.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PaymentDao paymentDao;

	public void addPayment(Payment payment) {
		// TODO Auto-generated method stub
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
			Boolean notClear, Date startDate, Date endDate, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, null, studentName, klass, school, null, 
				feeName, notClear, startDate, endDate);
	}

	public Page<Payment> listPaymentFromStudent(Integer page, Integer pageSize,
			ObjectId studentId, String feeName, Boolean notClear,
			Date startDate, Date endDate, String orderBy, String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, studentId, null, null, null, null, 
				feeName, notClear, startDate, endDate);
	}

	public Page<Payment> listPaymentFromFee(Integer page, Integer pageSize,
			ObjectId feeId, String studentName, String klass, String school,
			Boolean notClear, Date startDate, Date endDate, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		return paymentDao.findPayment(pageable, null, studentName, klass, school, feeId, 
				null, notClear, startDate, endDate);
	}

}
