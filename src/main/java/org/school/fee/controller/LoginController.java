package org.school.fee.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.school.fee.models.User;
import org.school.fee.service.UserService;
import org.school.fee.support.utils.Result;
import org.school.fee.support.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends AbstractController{
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserService userService;
	@RequestMapping("/")
	public void base(HttpServletResponse res) throws IOException{
		res.sendRedirect("action/student/");
	}
	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("/login");
		Subject user = SecurityUtils.getSubject();
		user.logout();
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(){
		logger.debug("url:{}","/action/login");
		return new ModelAndView("/login");
	}
	
	@RequestMapping("/postlogin")
	public ModelAndView postlogin(String username, String password,
			Boolean isRemember) {
		logger.debug("url:{}","/action/postlogin");
		logger.debug("username:{},password:{},isRemember:{}",new Object[]{username,password,isRemember});
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		if (isRemember!=null) {
			logger.debug("set remember");
			token.setRememberMe(true);
		}
		try {
			subject.login(token);
			User user = ShiroUtils.getCurrentUser();
			Calendar c = Calendar.getInstance();
			user.setLastLoginTime(c.getTime());
			userService.saveUser(user);
			logger.debug("login success");
			mv.setViewName("redirect:/");
			mv.addObject(subject.getPrincipal());
		} catch (Exception e) {
			mv.setViewName("/login");
			mv.addObject("result",new Result(e.getMessage(),"failure"));
		}
		return mv;
	}
}
