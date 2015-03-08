package com.school.fee.mongdb.test.service;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.User;
import org.school.fee.service.UserService;
import org.school.fee.support.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testFindByUsernameAndPassword(){
		String password = Md5Util.encrypt("admin", "qweasd123456");
		User user = userService.login("admin", password);
		assertNotNull(user);
		assertEquals(user.getUsername(),"admin");
		assertEquals(user.getNickname(),"神一样的管理员");
	}
}
