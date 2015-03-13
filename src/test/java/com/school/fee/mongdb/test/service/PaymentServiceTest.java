package com.school.fee.mongdb.test.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Payment;
import org.school.fee.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PaymentServiceTest {
	private Logger logger = LoggerFactory.getLogger(PaymentServiceTest.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	PaymentService paymentService;
	
	private Date now;
	
	@Before
	public void insertData(){
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setFeeName("學費");
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setStudentId(new ObjectId());
		payment.setStudentName("天天");
		payment.pay(new BigDecimal(100));
		payment.pay(new BigDecimal(200));
		payment.pay(new BigDecimal(300));
		payment.setSchool("補習班");
		payment.setKlass("一班");
		Calendar c = Calendar.getInstance();
		now = c.getTime();
		payment.setPayDate(now);
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),0,0,0);
		payment.setExpireDate(c.getTime());
		
		mongoTemplate.insert(payment);
		payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setFeeName("學雜費");
		payment.setFeeMoney(new BigDecimal(100));
		payment.setStudentId(new ObjectId());
		payment.setStudentName("羊羊羊");
		payment.pay(new BigDecimal(10));
		payment.pay(new BigDecimal(20));
		payment.pay(new BigDecimal(30));
		payment.setSchool("幼兒園");
		payment.setKlass("大班");
		c = Calendar.getInstance();
		payment.setPayDate(now);
		c.add(Calendar.DAY_OF_MONTH, 2);
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),0,0,0);
		payment.setExpireDate(c.getTime());
		
		mongoTemplate.insert(payment);
		long total = mongoTemplate.count(null,Payment.class);
		
		assertEquals(total, 2);
	}
	@After
	public void destroy(){
		mongoTemplate.dropCollection(Payment.class);
	}
	
	@Test
	public void testAddPayment(){
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setFeeName("學費");
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setStudentId(new ObjectId());
		payment.setStudentName("天天");
		payment.pay(new BigDecimal(100));
		payment.pay(new BigDecimal(200));
		payment.pay(new BigDecimal(300));
		payment.setSchool("補習班");
		payment.setKlass("一班");
		Calendar c = Calendar.getInstance();
		payment.setPayDate(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, 3);
		payment.setExpireDate(c.getTime());
		paymentService.addPayment(payment);
		long total = mongoTemplate.count(null,Payment.class);
		assertEquals(total, 3);
	}
	@Test
	public void testDeletePayment(){
		List<Payment> payments = mongoTemplate.findAll(Payment.class);
		ObjectId[] ids = new ObjectId[payments.size()]; 
		for(int i = 0,l = payments.size();i<l;i++){
			ids[i] = payments.get(i).getId();
		}
		paymentService.deletePayment(ids);
		assertEquals(0, mongoTemplate.count(null, Payment.class));
	}
	
	@Test
	public void testSavePayment(){
		Payment payment = mongoTemplate.findOne(query(where("feeName").is("學費")), Payment.class);
		payment.setSchool("幼兒園");
		paymentService.savePayment(payment);
		Payment paymentAfterSave = mongoTemplate.findOne(query(where("feeName").is("學費")), Payment.class);
		assertEquals(paymentAfterSave.getSchool(),"幼兒園");
	}
	
	@Test
	public void testGetPayment(){
		Payment payment = mongoTemplate.findOne(query(where("feeName").is("學費")), Payment.class);
		Payment paymentGetByService = paymentService.getPayment(payment.getId());
		assertEquals(paymentGetByService.getFeeName(),"學費");
	}
	
	@Test
	public void testListPayment() throws JsonParseException, JsonMappingException, IOException{
		Page<Payment> payments = paymentService.listPayment(0, 10, "天天", null, null, null, null, null, null, null, null);
		logger.debug("listPayment result by studentName:{}",payments.getContent());
		
		
		assertEquals(payments.getNumberOfElements(),1);
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DAY_OF_MONTH, -2);
		Date startDate = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, 2);
		Date endDate = c.getTime();
		payments = paymentService.listPayment(0, 10, "天天", "學費", "一班", "補習班", true, startDate, endDate, null, null);
		assertEquals(payments.getNumberOfElements(),1);
		logger.debug("listPayment result by all query:{}",payments.getContent());
		
		payments = paymentService.listPayment(0, 10, null, null, null, null, true, null, null, "expireDate","desc");
		assertEquals(payments.getNumberOfElements(),2);
		assertEquals(payments.getContent().get(0).getStudentName(),"羊羊羊");
		logger.debug("listPayment result with sort:{}",payments.getContent());
		
		payments = paymentService.listPayment(0, 1, null, null, null, null, true, null, null, "expireDate","desc");
		assertEquals(payments.getNumberOfElements(),1);
		assertEquals(payments.getContent().get(0).getStudentName(),"羊羊羊");
		logger.debug("listPayment result with pagination:{}",payments.getContent());
	}
	
	@Test
	public void testListPaymentFromStudent() throws JsonParseException, JsonMappingException, IOException{
		List<Payment> testPayment = mongoTemplate.findAll(Payment.class);
		Payment payment = testPayment.get(0);
		logger.debug("test payment is:{}",payment);
		Page<Payment> payments = paymentService.listPaymentFromStudent(0, 12,payment.getStudentId(), null, true, null, null,"expireDate", "asc");
		logger.debug("list payment by studentId with sort:{}",payments);
		assertEquals(payments.getNumberOfElements(),1);
		assertEquals(payments.getContent().get(0).getId(),payment.getId());
		
		payments = paymentService.listPaymentFromStudent(0, 12,payment.getStudentId(), null, false, null, null,null,null);
		logger.debug("list payment which not clear with sort:{}",payments);
		assertEquals(payments.getNumberOfElements(),0);
	}
	
	@Test
	public void testListPaymentFromFee() throws JsonParseException, JsonMappingException, IOException{
		List<Payment> testPayment = mongoTemplate.findAll(Payment.class);
		Payment payment = testPayment.get(0);
		logger.debug("test payment is:{}",payment);
		Page<Payment> payments = paymentService.listPaymentFromFee(0, 10, payment.getFeeId(), null, null, null, true, null, null, null, null);
		logger.debug("list payment by FeeId with sort:{}",payments);
		assertEquals(payments.getNumberOfElements(),1);
		assertEquals(payments.getContent().get(0).getId(),payment.getId());
		
		payments = paymentService.listPaymentFromFee(0, 10, payment.getFeeId(), null, null, null, false, null, null, null, null);
		logger.debug("list payment which not clear with sort:{}",payments);
		assertEquals(payments.getNumberOfElements(),0);
	}
	
	@Test
	public void testFindPaymentByExpireDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DAY_OF_MONTH, 2);
		List<Payment> payments = paymentService.findNotClearPaymentByDate(c.getTime());
		assertEquals(payments.size(),1);
		assertEquals(payments.get(0).getStudentName(),"天天");
	}
}
