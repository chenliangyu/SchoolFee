package com.school.fee.mongdb.test.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Message;
import org.school.fee.models.PayResult;
import org.school.fee.models.Payment;
import org.school.fee.models.Student;
import org.school.fee.models.User;
import org.school.fee.service.MessageService;
import org.school.fee.service.PaymentService;
import org.school.fee.service.StudentService;
import org.school.fee.service.UserService;
import org.school.fee.support.enums.InstalmentMethod;
import org.school.fee.support.enums.PayMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MessageServiceTest {
	private Logger logger = LoggerFactory.getLogger(MessageServiceTest.class);
	
	@Autowired
	MessageService messageService;
	@Autowired
	StudentService studentService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	UserService userService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	private User user;
	
	@Test
	public void testSendMessage() throws IOException{
		Student student = new Student();
		student.setName("多多");
		student.setPhone("12345678912");
		studentService.insertStudent(student);
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setStudentId(student.getId());
		payment.setId(new ObjectId());
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setFeeName("学费");
		payment.setStudentName(student.getName());
		payment.setSendNotify(true);
		payment.setSendMessage(true);
		payment.setPayMethod(PayMethod.instalment.ordinal());
		payment.setInstalment(5);
		payment.setExpireDayOfMonth(1);
		payment.setInstalmentMethod(InstalmentMethod.Month.ordinal());
		Date now = new Date();
		payment.setExpireDate(now);
		paymentService.addPayment(payment);
		PayResult payResult = new PayResult();
		payResult.setExpireDate(now);
		payResult.setMoney(BigDecimal.ZERO);
		payResult.setPayMoney(new BigDecimal(200));
		payResult.setStatus(1);
		payment.getPayResults().add(payResult);
		paymentService.pay(payment, new BigDecimal(300));
		messageService.sendMessage(payment);
		List<Message> msgs = mongoTemplate.findAll(Message.class);
		assertEquals(msgs.size(), 4);
		logger.debug(msgs.get(3).getMsgContent());
		mongoTemplate.dropCollection(Student.class);
		mongoTemplate.dropCollection(Payment.class);
	}
	
	@Test
	public void testSendSMS() throws IOException{
		Student student = new Student();
		student.setName("多多");
		student.setPhone("18520879240");
		studentService.insertStudent(student);
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setStudentId(student.getId());
		payment.setId(new ObjectId());
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setFeeName("学费");
		payment.setStudentName(student.getName());
		payment.setSendNotify(true);
		payment.setSendMessage(true);
		payment.setPayMethod(PayMethod.instalment.ordinal());
		payment.setInstalment(5);
		payment.setExpireDayOfMonth(1);
		payment.setInstalmentMethod(InstalmentMethod.Month.ordinal());
		Date now = new Date();
		payment.setExpireDate(now);
		paymentService.addPayment(payment);
		PayResult payResult = new PayResult();
		payResult.setExpireDate(now);
		payResult.setMoney(BigDecimal.ZERO);
		payResult.setPayMoney(new BigDecimal(200));
		payResult.setStatus(1);
		payment.getPayResults().add(payResult);
		paymentService.pay(payment, new BigDecimal(300));
		messageService.sendSMS(student, payment);
		mongoTemplate.dropCollection(Student.class);
		mongoTemplate.dropCollection(Payment.class);
	}
	
	@Test
	public void testSendNotify() throws IOException{
		Student student = new Student();
		student.setName("多多");
		student.setPhone("12345678912");
		studentService.insertStudent(student);
		Payment payment = new Payment();
		payment.setFeeId(new ObjectId());
		payment.setStudentId(student.getId());
		payment.setId(new ObjectId());
		payment.setFeeMoney(new BigDecimal(1000));
		payment.setFeeName("学费");
		payment.setStudentName(student.getName());
		payment.setSendNotify(true);
		payment.setSendMessage(true);
		payment.setPayMethod(PayMethod.instalment.ordinal());
		payment.setInstalment(5);
		payment.setExpireDayOfMonth(1);
		payment.setInstalmentMethod(InstalmentMethod.Month.ordinal());
		Date now = new Date();
		payment.setExpireDate(now);
		paymentService.addPayment(payment);
		messageService.sendNotify(student, payment);
		List<Message> msgs = mongoTemplate.findAll(Message.class);
		assertEquals(msgs.size(), 4);
		logger.debug(msgs.get(3).getMsgContent());
		mongoTemplate.dropCollection(Student.class);
		mongoTemplate.dropCollection(Payment.class);
	}
	
	//@Test
	public void testSend() throws IOException{
		for(int i = 0,l = 20;i<l;i++){
			testSendNotify();
		}
	}
	
	@org.junit.Before
	public void insertData(){
		user = userService.findAdmin();
		Message msg = new Message();
		msg.setMsgContent("这是一个测试消息");
		msg.setUserId(user.getId());
		mongoTemplate.insert(msg);
		msg = new Message();
		msg.setMsgContent("这是另一个测试消息");
		msg.setUserId(user.getId());
		mongoTemplate.insert(msg);
		msg = new Message();
		msg.setMsgContent("这是第三个测试消息");
		msg.setUserId(user.getId());
		mongoTemplate.save(msg);
		List<Message> msgs = mongoTemplate.findAll(Message.class);
		assertEquals(3, msgs.size());
		assertNotNull(msgs.get(0).getCreateDate());
	}
	@org.junit.After
	public void destory(){
		mongoTemplate.dropCollection(Message.class);
	}
	@Test
	public void testCountNewMessage(){
		long count = messageService.countNewMessage(user.getId());
		assertEquals(3, count);
	}
	@Test
	public void testUpdateAllNewMessage(){
		messageService.updateAllNewMessage(user.getId());
		long count = mongoTemplate.count(query(where("userId").is(user.getId()).and("isNew").is(true)), Message.class);
		assertEquals(0,count);
	}
	
	@Test
	public void testListMessage(){
		Page<Message> message = messageService.listMessage(0, 10, user.getId());
		assertEquals(3,message.getNumberOfElements());
		message = messageService.listMessage(0, 1, user.getId());
		assertEquals(1,message.getNumberOfElements());
	}
	
	@Test
	public void testDeleteMessage(){
		List<Message> messag= mongoTemplate.findAll(Message.class);
		ObjectId[] ids = new ObjectId[3];
		int i = 0;
		for(Message message :messag){
			ids[i] = message.getId();
			i++;
		}
		messageService.deleteMessage(ids);
		messag= mongoTemplate.findAll(Message.class);
		assertEquals(0,messag.size());
	}
	
}
