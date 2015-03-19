package com.school.fee.mongdb.test.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Fee;
import org.school.fee.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FeeServiceTest {
	@Autowired
	FeeService feeService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Test
	public void testAddFee(){
		Fee fee = new Fee();
		fee.setMoney(new BigDecimal(1000.10));
		fee.setName("学费");
		feeService.insertFee(fee);
		assertNotNull(fee.getId());
		List<Fee> feesInDb = mongoTemplate.findAll(Fee.class);
		assertEquals(4, feesInDb.size());
		Fee feeInDb = feesInDb.get(3);
		assertEquals(feeInDb.getId(),fee.getId());
	}
	@Before
	public void InsertData(){
		Fee fee = new Fee();
		fee.setMoney(new BigDecimal(1000.10));
		fee.setName("学费");
		feeService.insertFee(fee);
		fee = new Fee();
		fee.setMoney(new BigDecimal(1002.20));
		fee.setName("学杂费");
		feeService.insertFee(fee);
		fee = new Fee();
		fee.setMoney(new BigDecimal(2003.20));
		fee.setName("生活费");
		feeService.insertFee(fee);
		assertEquals(3,mongoTemplate.findAll(Fee.class).size());
	}
	@After
	public void dropCollection(){
		mongoTemplate.dropCollection(Fee.class);
	}
	
	@Test
	public void testSaveFee(){
		Fee fee = mongoTemplate.findOne(query(where("name").is("学费")), Fee.class);
		fee.setName("学费改");
		feeService.saveFee(fee);
		Fee feeInDb = mongoTemplate.findOne(query(where("name").is("学费改")), Fee.class);
		assertEquals(feeInDb.getId(), fee.getId());
	}
	
	@Test
	public void testListFee(){
		List<Fee> fee = feeService.listFee("学");
		assertEquals(fee.size(), 2);
		List<Fee> fee1 = feeService.listFee(null);
		assertEquals(fee1.size(),3);
	}
	
	@Test
	public void testDeleteFee(){
		List<Fee> fees = mongoTemplate.findAll(Fee.class);
		for(Fee fee:fees){
			feeService.deleteFee(fee.getId());
		}
		List<Fee> feesAfterDelete = mongoTemplate.findAll(Fee.class);
		assertEquals(feesAfterDelete.size(),0);
	}
}
