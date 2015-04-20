package org.school.fee.controller;

import java.text.MessageFormat;

import org.school.fee.service.MessageService;
import org.school.fee.support.utils.Result;
import org.school.fee.support.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController extends AbstractController {

	@Autowired
	MessageService messageService;
	
	@RequestMapping("/sendsms")
	public ModelAndView testSendSms(){
		return new ModelAndView("test/sendsms");
	}
	@RequestMapping("/postsend")
	public ModelAndView postSend(String phone,String studentName,String feeName,String total,String rest){
		String template = messageService.getSMSTemplate();
		String msg = MessageFormat.format(template,studentName,feeName,total,rest);
		SMSUtils.sendSMS(phone, msg);
		return new ModelAndView("test/sendsms").addObject("result", new Result("发送成功，短信会在3-5分钟之内发送到你的手机上，注意查收","success"));
	}
}
