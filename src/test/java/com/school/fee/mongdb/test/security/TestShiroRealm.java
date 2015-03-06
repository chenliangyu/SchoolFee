package com.school.fee.mongdb.test.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.User;
import org.school.fee.support.security.ShiroRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestShiroRealm extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private ShiroRealm shiroRealm;
	
	@Test
	public void testShiroRealm(){
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername("admin");
		token.setPassword("qweasd123456".toCharArray());
		AuthenticationInfo info = shiroRealm.getAuthenticationInfo(token);
		User user = (User)info.getPrincipals().getPrimaryPrincipal();
		assertNotNull(user);
		assertEquals(user.getUsername(),"admin");
	}
}
