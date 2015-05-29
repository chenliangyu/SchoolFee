package org.school.fee.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.school.fee.models.PayAnalytics;
import org.school.fee.models.Payment;
import org.school.fee.service.AnalyticsService;
import org.school.fee.support.enums.PayMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
	
	
	@Autowired
	AnalyticsService analyticsService;
	
	@RequestMapping("/list/")
	public ModelAndView list(){
		return new ModelAndView("forward:/action/analytics/list/0");
	}
	
	@RequestMapping("/list/{pageNo}")
	public ModelAndView listByPage(@PathVariable Integer pageNo,Integer pageSize,
			String feeId,String studentName,String klass,String feeName,String school,Boolean notClear,PayMethod payMethod,
			Date startDate,Date endDate){
		logger.debug("uri:{}","/action/analytics/list/");
		logger.debug("pageParam[page:{},pageSize:{}]",pageNo,pageSize);
		logger.debug("filterParam[studentName:{},startDate:{},endDate:{},notClear:{},klass:{},school:{}]",
				new Object[]{studentName,startDate,endDate,notClear,klass,school});
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> filter = new HashMap<String,Object>();
		ObjectId fee = null;
		if(studentName!=null && !studentName.equals("")){
			filter.put("studentName", studentName);
		}
		if(klass!=null && !klass.equals("")){
			filter.put("klass", klass);
		}
		if(school!=null && !school.equals("")){
			filter.put("school", school);
		}
		if(feeName!=null && !feeName.equals("")){
			filter.put("feeName", feeName);
		}
		if(payMethod!=null){
			filter.put("payMethod",payMethod);
		}
		if(startDate!=null && !startDate.equals("")){
			filter.put("startDate", startDate);
		}
		if(endDate!=null && !endDate.equals("")){
			filter.put("endDate", endDate);
		}
		if(notClear!=null){
			filter.put("notClear", notClear);
		}
		if(feeId!=null && ObjectId.isValid(feeId)){
			fee = new ObjectId(feeId);
		}
		result.put("filter", filter);
		result.put("hasFilter",filter.keySet().size() != 0);
		Page<PayAnalytics> analytics = analyticsService.analytics(pageNo, pageSize,
				fee,feeName,studentName,klass,school,notClear,payMethod,startDate,endDate);
		result.put("analytics", analytics);
		return new ModelAndView("/analysis/index").addObject("result", result);
	}
}
