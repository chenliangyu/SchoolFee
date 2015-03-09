package com.school.fee.mongdb.test.service;

import static org.junit.Assert.*;

import java.util.List;

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
		fee.setMoney(1000.10);
		fee.setName("学费");
		feeService.insertFee(fee);
		assertNotNull(fee.getId());
		List<Fee> feesInDb = mongoTemplate.findAll(Fee.class);
		assertEquals(1, feesInDb.size());
		Fee feeInDb = feesInDb.get(0);
		assertEquals(feeInDb.getId(),fee.getId());
		mongoTemplate.dropCollection(Fee.class);
	}
	public void InsertData(){
		Fee fee = new Fee();
		fee.setMoney(1000.10);
		fee.setName("学费");
		feeService.insertFee(fee);
		fee = new Fee();
		fee.setMoney(1002.20);
		fee.setName("学杂费");
		feeService.insertFee(fee);
		fee = new Fee();
		fee.setMoney(2003.20);
		fee.setName("生活费");
		feeService.insertFee(fee);
		assertEquals(3,mongoTemplate.findAll(Fee.class).size());
	}
	@Test
	public void testSaveFee(){
		InsertData();
		Fee fee = mongoTemplate.findOne(query(where("name").is("学费")), Fee.class);
		fee.setName("学费改");
		feeService.saveFee(fee);
		Fee feeInDb = mongoTemplate.findOne(query(where("name").is("学费改")), Fee.class);
		assertEquals(feeInDb.getId(), fee.getId());
		mongoTemplate.dropCollection(Fee.class);
	}
	
	@Test
	public void testListFee(){
		InsertData();
		Page<Fee> fee = feeService.listFee(0,20, "学", null, null);
		assertEquals(fee.getNumberOfElements(), 2);
		Page<Fee> fee1 = feeService.listFee(0,2,null, null, null);
		assertEquals(fee1.getNumberOfElements(),2);
		assertEquals(fee1.getTotalPages(),2);
		assertEquals(fee1.getTotalElements(),3);
		Page<Fee> fee2 = feeService.listFee(0,20, null, "money", "asc");
		assertEquals(fee2.getContent().get(0).getName(),"学费");
		mongoTemplate.dropCollection(Fee.class);
	}
	
	@Test
	public void testDeleteFee(){
		InsertData();
		List<Fee> fees = mongoTemplate.findAll(Fee.class);
		for(Fee fee:fees){
			feeService.deleteFee(fee.getId());
		}
		List<Fee> feesAfterDelete = mongoTemplate.findAll(Fee.class);
		assertEquals(feesAfterDelete.size(),0);
	}
}
