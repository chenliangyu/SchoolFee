package org.school.fee.support.security;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.school.fee.models.User;
import org.school.fee.service.UserService;
import org.school.fee.support.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ShiroRealm extends AuthorizingRealm implements ApplicationContextAware{
	private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	private UserService userService;
	
	private ApplicationContext applicationContext;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		logger.debug("start check login user");
		User user = (User) principals.getPrimaryPrincipal();
		logger.debug("current user is a {}", user.getRole());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(user.getRole());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.debug("start logining");
		UsernamePasswordToken user = (UsernamePasswordToken) token;
		String username = user.getUsername();
		String password = new String(user.getPassword());
		String md5Password = Md5Util.encrypt(username, password);
		User shiroUser = userService.login(username, md5Password);
		if (shiroUser != null) {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
					shiroUser, password, getName());
			return info;
		} else {
			throw new AuthenticationException("用户名或者密码错误");
		}
	}
	public void setUserService(){
		this.userService = applicationContext.getBean(UserService.class);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
}
