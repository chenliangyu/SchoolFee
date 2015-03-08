package org.school.fee.controller;

import org.school.fee.models.Fee;
import org.school.fee.service.FeeService;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fee")
public class FeeController extends AbstractController{
	private Logger logger = LoggerFactory.getLogger(FeeController.class);
	@Autowired
	FeeService feeService;
	
	@RequestMapping("/")
	public ModelAndView list(){
		logger.debug("uri:{}","/action/fee/");
		return new ModelAndView("/fee/list");
	}
	
	@RequestMapping("/fee/add")
	public ModelAndView add(){
		logger.debug("uri:{}","/action/fee/add");
		return new ModelAndView("fee/add");
	}
	
	@RequestMapping("/fee/postadd")
	public ModelAndView postadd(Fee fee){
		logger.debug("uri:{}","/action/fee/postadd");
		logger.debug("fee:{}",fee);
		feeService.saveFee(fee);
		Result result = new Result("添加成功","success");
		return new ModelAndView("fee/add").addObject("result", result);
	}
	
	@RequestMapping("/fee/pay")
	public ModelAndView pay(Integer studentId){
		return new ModelAndView("fee/pay");
	}
	
}
