package org.school.fee.controller;

import java.util.List;

import org.bson.types.ObjectId;
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
	public ModelAndView list(String name){
		logger.debug("uri:{}","/action/fee/");
		logger.debug("name filter : {}",name);
		List<Fee> fees = feeService.listFee(name);
		return new ModelAndView("/fee/list").addObject("result", new Result("success",fees));
	}
	@RequestMapping("/postadd")
	public ModelAndView postadd(Fee fee){
		logger.debug("uri:{}","/action/fee/postadd");
		logger.debug("fee:{}",fee);
		Boolean isModify = fee.getId() != null;
		if(isModify){
			feeService.saveFee(fee);
		}else{
			feeService.insertFee(fee);
		}
		return new ModelAndView("fee/add").addObject("result", new Result("添加成功","success"));
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(ObjectId[] ids){
		logger.debug("uri:{}","/action/fee/delete");
		logger.debug("ids:{}",ids);
		feeService.deleteFee(ids);
		return new ModelAndView("fee/delete").addObject("result", new Result("删除成功","success"));
	}
}
