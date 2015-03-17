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
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Payment;
import org.school.fee.service.PaymentService;
import org.school.fee.support.enums.InstalmentMethod;
import org.school.fee.support.enums.PayMethod;
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
		payment.setSchool("補習班");
		payment.setKlass("一班");
		payment.setExpireDayOfWeek(3);//周三
		payment.setPayMethod(PayMethod.instalment.ordinal());
		payment.setInstalmentMethod(InstalmentMethod.Week.ordinal());
		payment.setInstalment(5);
		paymentService.addPayment(payment);
		
		payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setFeeName("學雜費");
		payment.setFeeMoney(new BigDecimal(100));
		payment.setStudentId(new ObjectId());
		payment.setStudentName("羊羊羊");
		payment.setSchool("幼兒園");
		payment.setKlass("大班");
		payment.setExpireDate(DateTime.now().plusDays(1).hourOfDay().setCopy(0).minuteOfHour().setCopy(0).secondOfMinute().setCopy(0).toDate());
		payment.setPayMethod(PayMethod.onePay.ordinal());
		paymentService.addPayment(payment);
		long total = mongoTemplate.count(null,Payment.class);
		assertEquals(total, 2);
	}
	@After
	public void destroy(){
		mongoTemplate.dropCollection(Payment.class);
	}
	
	@Test
	public void testAddOnePayPayment(){
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setFeeName("學費");
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setStudentId(new ObjectId());
		payment.setStudentName("天天");
		payment.setSchool("補習班");
		payment.setKlass("一班");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 3);
		payment.setExpireDate(c.getTime());
		payment.setPayMethod(PayMethod.onePay.ordinal());
		paymentService.addPayment(payment);
		long total = mongoTemplate.count(null,Payment.class);
		assertEquals(total, 3);
	}
	
	@Test
	public void testPay(){
		Payment payment = mongoTemplate.findOne(query(where("studentName").is("羊羊羊")), Payment.class);
		paymentService.pay(payment, new BigDecimal(10));
		paymentService.pay(payment, new BigDecimal(90));
		assertEquals(payment.getMoney().size(), 2);
		assertEquals(payment.getPayResults().size(), 1);
		logger.debug("payment:{}",payment);
		
		Payment payment1 = mongoTemplate.findOne(query(where("studentName").is("天天")), Payment.class);
		paymentService.pay(payment1, new BigDecimal(700));
		paymentService.pay(payment1, new BigDecimal(200));
		paymentService.pay(payment1, new BigDecimal(100));
		logger.debug("payment:{}",payment1);
		assertEquals(payment1.getMoney().size(), 3);
		assertEquals(payment1.getPayResults().size(), 5);
		
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
		Date startDate = DateTime.now().toDate();
		Date endDate = DateTime.now().dayOfWeek().setCopy(3).plusWeeks(1).toDate();
		payments = paymentService.listPayment(0, 10, "天天", "學費", "一班", "補習班", true, startDate, endDate, null, null);
		assertEquals(payments.getNumberOfElements(),1);
		logger.debug("listPayment result by all query:{}",payments.getContent());
		
		payments = paymentService.listPayment(0, 10, null, null, null, null, true, null, null, "createDate","desc");
		assertEquals(payments.getNumberOfElements(),2);
		assertEquals(payments.getContent().get(0).getStudentName(),"羊羊羊");
		logger.debug("listPayment result with sort:{}",payments.getContent());
		
		payments = paymentService.listPayment(1, 1, null, null, null, null, true, null, null, "createDate","desc");
		assertEquals(payments.getNumberOfElements(),1);
		assertEquals(payments.getContent().get(0).getStudentName(),"天天");
		logger.debug("listPayment result with pagination:{}",payments.getContent());
	}
	
	@Test
	public void testListPaymentFromStudent() throws JsonParseException, JsonMappingException, IOException{
		List<Payment> testPayment = mongoTemplate.findAll(Payment.class);
		Payment payment = testPayment.get(0);
		logger.debug("test payment is:{}",payment);
		Page<Payment> payments = paymentService.listPaymentFromStudent(0, 12,payment.getStudentId(), null, true, null, null,"createDate", "asc");
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
		c.add(Calendar.DAY_OF_MONTH, 2);
		List<Payment> payments = paymentService.findNotClearPaymentByDate(c.getTime());
		assertEquals(payments.size(),2);
		assertEquals(payments.get(0).getStudentName(),"天天");
	}
}
