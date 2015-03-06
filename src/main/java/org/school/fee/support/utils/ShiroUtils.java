package org.school.fee.support.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.school.fee.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroUtils {
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	public static User getCurrentUser(){
		return (User)getSubject().getPrincipal(); 
	}
	public static Session getSession(Boolean flag){
		return getSubject().getSession(flag);
	}
}
