package org.school.fee.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.school.fee.service.FeeService;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("feeList", fees);
		if(name!=null && !name.equals("")){
			result.put("name", name);
		}
		return new ModelAndView("/fee/index").addObject("result", new Result("success",result));
	}
	@RequestMapping("/postadd")
	public ModelAndView postadd(Fee fee){
		logger.debug("uri:{}","/action/fee/postadd");
		logger.debug("fee:{}",fee);
		Result result = new Result();
		Boolean isModify = fee.getId() != null;
		result.setData(isModify);
		if(isModify){
			feeService.saveFee(fee);
			result.setMsg("修改成功");
		}else{
			feeService.insertFee(fee);
			result.setMsg("添加成功");
		}
		result.setCode("success");
		return new ModelAndView("fee/index").addObject("result", result);
	}
	
	@RequestMapping("/get/{feeId}")
	public ModelAndView get(@PathVariable ObjectId feeId){
		Fee fee = feeService.getFee(feeId);
		return new ModelAndView("/fee/index").addObject("result",new Result("success",fee));
	}
	@RequestMapping("/delete")
	public ModelAndView delete(String ids) throws JsonParseException, JsonMappingException, IOException{
		logger.debug("url:{}","/action/student/delete");
		logger.debug("ids:{}",ids);
		ObjectId[] idArray = (new ObjectMapper()).readValue(ids, ObjectId[].class);
		feeService.deleteFee(idArray);
		return new ModelAndView("fee/index").addObject("result",new Result("删除成功","success"));
	}
	
	@RequestMapping("/delete/{feeId}")
	public ModelAndView delete(@PathVariable ObjectId feeId){
		logger.debug("uri:{}","/action/fee/delete");
		logger.debug("id:{}",feeId);
		feeService.deleteFee(feeId);
		return new ModelAndView("fee/index").addObject("result", new Result("删除成功","success"));
	}
	
}
