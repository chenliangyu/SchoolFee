package com.school.fee.mongdb.test.service;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.dao.UserDao;
import org.school.fee.models.User;
import org.school.fee.support.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testFindByUsernameAndPassword(){
		String password = Md5Util.encrypt("admin", "qweasd123456");
		List<User> users = userDao.findByUsernameAndPassword("admin", password);
		assertNotNull(users);
		assertEquals(users.size(), 1);
		User user = users.get(0);
		assertEquals(user.getUsername(),"admin");
		assertEquals(user.getNickname(),"神一样的管理员");
	}
}
