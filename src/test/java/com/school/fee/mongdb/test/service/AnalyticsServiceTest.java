package com.school.fee.mongdb.test.service;

import java.math.BigDecimal;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Fee;
import org.school.fee.models.PayAnalytics;
import org.school.fee.models.PayAnalyticsForKlassAndSchool;
import org.school.fee.models.Payment;
import org.school.fee.models.Student;
import org.school.fee.service.AnalyticsService;
import org.school.fee.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AnalyticsServiceTest {
	@Autowired
	AnalyticsService analyticsService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void insertData(){
		Payment payment = new Payment();
		Payment payment2 = new Payment();
		Payment payment3 = new Payment();
		Payment payment4 = new Payment();
		
		
		Fee fee = new Fee();
		fee.setId(new ObjectId());
		fee.setMoney(new BigDecimal(1200));
		fee.setName("作业辅导费");
		Fee fee2 = new Fee();
		fee2.setId(new ObjectId());
		fee2.setMoney(new BigDecimal(500));
		fee2.setName("英语辅导费");
		
		
		Student student = new Student();
		student.setId(new ObjectId());
		student.setKlass("一班");
		student.setSchool("补习班");
		Student student2 = new Student();
		student2.setId(new ObjectId());
		student2.setKlass("二班");
		student2.setSchool("补习班");
		
		
		payment.setFeeId(fee.getId());
		payment.setFeeName(fee.getName());
		payment.setFeeMoney(fee.getMoney());
		payment.setStudentId(student.getId());
		payment.setKlass(student.getKlass());
		payment.setSchool(student.getSchool());
		payment.setPayMethod(0);
		payment.setExpireDate(DateTime.now().plusMonths(1).toDate());
		paymentService.addPayment(payment);
		paymentService.pay(payment, new BigDecimal(1000));
		
		payment2.setFeeId(fee2.getId());
		payment2.setFeeName(fee2.getName());
		payment2.setFeeMoney(fee2.getMoney());
		payment2.setStudentId(student.getId());
		payment2.setKlass(student.getKlass());
		payment2.setSchool(student.getSchool());
		payment2.setPayMethod(1);
		payment2.setInstalment(2);
		payment2.setExpireDayOfMonth(25);
		payment2.setInstalmentMethod(0);
		paymentService.addPayment(payment2);
		paymentService.pay(payment2, new BigDecimal(250));
		
		payment3.setFeeId(fee.getId());
		payment3.setFeeName(fee.getName());
		payment3.setFeeMoney(fee.getMoney());
		payment3.setStudentId(student2.getId());
		payment3.setKlass(student2.getKlass());
		payment3.setSchool(student2.getSchool());
		payment3.setPayMethod(0);
		payment3.setExpireDate(DateTime.now().plusMonths(1).toDate());
		paymentService.addPayment(payment3);
		paymentService.pay(payment3, new BigDecimal(1200));
		
		payment4.setFeeId(fee2.getId());
		payment4.setFeeName(fee2.getName());
		payment4.setFeeMoney(fee2.getMoney());
		payment4.setStudentId(student2.getId());
		payment4.setKlass(student2.getKlass());
		payment4.setSchool(student2.getSchool());
		payment4.setPayMethod(1);
		payment4.setInstalment(2);
		payment4.setExpireDayOfMonth(25);
		payment4.setInstalmentMethod(0);
		paymentService.addPayment(payment4);
		paymentService.pay(payment4, new BigDecimal(500));
	}
	
	@After
	public void destoryData(){
		mongoTemplate.dropCollection(Payment.class);
	}
	
	@Test
	public void testListAnalytics(){
		Page<PayAnalytics> lists = analyticsService.analytics(0, 10,null,null,null,null,null,null,null,null);
		assertEquals(lists.getNumberOfElements(), 2);
		PayAnalytics result1 = lists.getContent().get(0);
		assertEquals(result1.getFeeName(),"英语辅导费");
		PayAnalyticsForKlassAndSchool singleResult3 = result1.getResult().get(0);
		assertEquals(result1.getResult().size(), 2);
		assertEquals(singleResult3.getKlass(),"一班");
		assertEquals(singleResult3.getSchool(),"补习班");
		assertEquals(singleResult3.getHasPayStudentNumber(),1);
		assertEquals(singleResult3.getClearStudentNumber(),0);
		assertEquals(singleResult3.getNotClearStudentNumber(),1);
		assertEquals(singleResult3.getInstalmentClearStudentNumber(),0);
		assertEquals(singleResult3.getInstalmentStudentNumber(),1);
		assertEquals(singleResult3.getInstalmentNotClearStudentNumber(),1);
		assertEquals(singleResult3.getOnepayStudentNumber(),0);
		assertEquals(singleResult3.getOnepayNotClearStudentNumber(),0);
		assertEquals(singleResult3.getOnepayClearStudentNumber(),0);
		assertEquals(singleResult3.getTotal(),new BigDecimal(500));
		assertEquals(singleResult3.getPayTotal(),new BigDecimal(250));
		assertEquals(singleResult3.getOnepayTotal(),new BigDecimal(0));
		assertEquals(singleResult3.getOnepayPayTotal(),new BigDecimal(0));
		assertEquals(singleResult3.getInstalmentTotal(),new BigDecimal(500));
		assertEquals(singleResult3.getInstalmentPayTotal(),new BigDecimal(250));
		
		
		PayAnalytics result2 = lists.getContent().get(1);
		assertEquals(result2.getFeeName(),"作业辅导费");
		PayAnalyticsForKlassAndSchool singleResult1 = result2.getResult().get(1);
		
		assertEquals(result2.getResult().size(), 2);
		assertEquals(singleResult1.getKlass(),"二班");
		assertEquals(singleResult1.getSchool(),"补习班");
		assertEquals(singleResult1.getHasPayStudentNumber(),1);
		assertEquals(singleResult1.getClearStudentNumber(),1);
		assertEquals(singleResult1.getNotClearStudentNumber(),0);
		assertEquals(singleResult1.getInstalmentClearStudentNumber(),0);
		assertEquals(singleResult1.getInstalmentStudentNumber(),0);
		assertEquals(singleResult1.getInstalmentNotClearStudentNumber(),0);
		assertEquals(singleResult1.getOnepayStudentNumber(),1);
		assertEquals(singleResult1.getOnepayNotClearStudentNumber(),0);
		assertEquals(singleResult1.getOnepayClearStudentNumber(),1);
		assertEquals(singleResult1.getTotal(),new BigDecimal(1200));
		assertEquals(singleResult1.getPayTotal(),new BigDecimal(1200));
		assertEquals(singleResult1.getOnepayTotal(),new BigDecimal(1200));
		assertEquals(singleResult1.getOnepayPayTotal(),new BigDecimal(1200));
		assertEquals(singleResult1.getInstalmentTotal(),new BigDecimal(0));
		assertEquals(singleResult1.getInstalmentPayTotal(),new BigDecimal(0));
	}
}
