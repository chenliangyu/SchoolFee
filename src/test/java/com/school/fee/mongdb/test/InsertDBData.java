package com.school.fee.mongdb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.User;
import org.school.fee.support.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InsertDBData extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private MongoTemplate mongoTemplate;  
	@Test
	public void InsertUserTest(){
		mongoTemplate.dropCollection(User.class);
		User user =  new User();
		user.setUsername("admin");
		user.setPassword(Md5Util.encrypt("admin", "qweasd123456"));
		user.setRole("admin");
		user.setNickname("神一样的管理员");
		mongoTemplate.insert(user);
	}
}
