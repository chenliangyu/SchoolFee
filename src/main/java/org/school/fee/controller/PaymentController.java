package org.school.fee.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.school.fee.models.Payment;
import org.school.fee.models.Student;
import org.school.fee.service.FeeService;
import org.school.fee.service.PaymentService;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.Constants;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/payment")
public class PaymentController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	PaymentService paymentService;
	@Autowired
	StudentService studentService;
	@Autowired
	FeeService feeService;
	
	@RequestMapping("/student/{studentId}/{page}")
	public ModelAndView toPay(@PathVariable Integer page,Integer pageSize,
			@PathVariable ObjectId studentId,Boolean notClear,String feeName,	Date startDate,Date endDate,
			String orderBy,String order){
		logger.debug("uri:{}","/action/payment/"+studentId+"/");
		Student student = studentService.getStudent(studentId);
		List<Fee> feeList = feeService.listFee(null); 
		logger.debug("student:{}",student);
		if(page == null){
			page = 0;
		}
		logger.debug("pageParam[page:{},pageSize:{}]",page,pageSize);
		logger.debug("sortParam[orderBy:{},order:{}]",orderBy,order);
		logger.debug("filterParam[feeName:{},startDate:{},endDate:{},notClear:{}]",
				new Object[]{feeName,startDate,endDate,notClear});
		Page<Payment> payments = paymentService.listPaymentFromStudent(page, pageSize,studentId,
				feeName,notClear,startDate,endDate,orderBy,order); 
		logger.debug("total:{}",payments.getTotalElements());
		logger.debug("currentTotal:{}",payments.getNumberOfElements());
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> filter = new HashMap<String,Object>();
		if(feeName!=null && !feeName.equals("")){
			filter.put("feeName", feeName);
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
		result.put("filter", filter);
		result.put("hasFilter",filter.keySet().size() != 0);
		result.put("student", student);
		result.put("payments", payments);
		result.put("feeList", feeList);
		return new ModelAndView("/payment/index").addObject("result",result);
	}
	@RequestMapping("/add")
	public ModelAndView add(Payment payment,BigDecimal payMoney){
		logger.debug("uri:{}","/action/payment/add");
		/*Payment payment = new Payment();
		payment.setPayMethod(payMethod);
		payment.setExpireDate(expireDate);
		payment.setSendMessage(sendMessage);
		payment.setSmsInterval(smsInterval);
		payment.setSmsPeriod(smsPeriod);
		payment.setSendNotify(sendNotify);
		payment.setInstalment(instalment);
		payment.setInstalmentMethod(instalmentMethod);
		payment.setExpireDayOfMonth(expireDayOfMonth);
		payment.setExpireDayOfWeek(expireDayOfWeek);*/
		logger.debug("payment:{}",payment);
		Student student = studentService.getStudent(payment.getStudentId());
		Fee fee = feeService.getFee(payment.getFeeId());
		payment.setFeeName(fee.getName());
		payment.setFeeMoney(fee.getMoney());
		payment.setStudentName(student.getName());
		payment.setKlass(student.getKlass());
		payment.setSchool(student.getSchool());
		paymentService.addPayment(payment);
		if(payMoney!=null && payMoney.compareTo(BigDecimal.ZERO)>0){
			paymentService.pay(payment, payMoney);
			//paymentService.pay(payment, money);
		}
		return new ModelAndView("/payment/index").addObject("result", new Result("添加成功","success"));
	}
	
	@RequestMapping("/pay/{paymentId}/{money}")
	public ModelAndView pay(@PathVariable ObjectId paymentId,@PathVariable BigDecimal money){
		logger.debug("uri:{}","/action/pay/"+paymentId);
		Payment payment = paymentService.getPayment(paymentId);
		logger.debug("payment : {}",payment);
		paymentService.pay(payment, money);
		return new ModelAndView("/payment/index").addObject("result",new Result("缴费成功","success"));
	}
	
	@RequestMapping("/fee/{feeId}/{page}")
	public ModelAndView feeToPay(@PathVariable ObjectId feeId,@PathVariable Integer page,
			Integer pageSize,String studentName,String klass,String school,Boolean notClear,
			Date startDate,Date endDate,String orderBy,String order){
		logger.debug("uri:{}","/action/payment/"+feeId+"/");
		logger.debug("pageParam[page:{},pageSize:{}]",page,pageSize);
		logger.debug("sortParam[orderBy:{},order:{}]",orderBy,order);
		logger.debug("filterParam[studentName:{},startDate:{},endDate:{},notClear:{},klass:{},school:{}]",
				new Object[]{studentName,startDate,endDate,notClear,klass,school});
		Page<Payment> payments = paymentService.listPaymentFromFee(page, pageSize,feeId,studentName,
				klass,school,notClear,startDate,endDate,orderBy,order);
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> filter = new HashMap<String,Object>();
		if(studentName!=null && !studentName.equals("")){
			filter.put("studentName", studentName);
		}
		if(klass!=null && !klass.equals("")){
			filter.put("klass", klass);
		}
		if(school!=null && !school.equals("")){
			filter.put("school", school);
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
		result.put("filter", filter);
		result.put("hasFilter",filter.keySet().size() != 0);
		result.put("feeId", feeId);
		result.put("payments", payments);
		return new ModelAndView("/payment/index").addObject("result",result);
	}
	
	@RequestMapping("/")
	public ModelAndView paymentlist(){
		return new ModelAndView("forward:/action/payment/0");
	}
	
	@RequestMapping("/{page}")
	public ModelAndView feeList(@PathVariable Integer page,Integer pageSize,
			String studentName,String feeName,String klass,String school,Boolean notClear,
			Date startDate,Date endDate,String orderBy,	String order){
		logger.debug("uri:{}","/action/payment/");
		logger.debug("pageParam[page:{},pageSize:{}]",page,pageSize);
		logger.debug("sortParam[orderBy:{},order:{}]",orderBy,order);
		logger.debug("filterParam[studentName:{},feeName:{},startDate:{},endDate:{},notClear:{},klass:{},school:{}]",
				new Object[]{studentName,feeName,startDate,endDate,notClear,klass,school});
		Page<Payment> payments = paymentService.listPayment(page, pageSize,studentName,feeName,
				klass,school,notClear,startDate,endDate,orderBy,order);
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> filter = new HashMap<String,Object>();
		if(studentName!=null && !studentName.equals("")){
			filter.put("studentName", studentName);
		}
		if(feeName!=null && !feeName.equals("")){
			filter.put("feeName", feeName);
		}
		if(klass!=null && !klass.equals("")){
			filter.put("klass", klass);
		}
		if(school!=null && !school.equals("")){
			filter.put("school", school);
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
		result.put("filter", filter);
		result.put("hasFilter",filter.keySet().size() != 0);
		result.put("payments", payments);
		return new ModelAndView("/payment/index").addObject("result",result);
	}
	@RequestMapping("/delete")
	public ModelAndView delete(String ids) throws JsonParseException, JsonMappingException, IOException{
		logger.debug("url:{}","/action/student/delete");
		logger.debug("ids:{}",ids);
		ObjectId[] idArray = (new ObjectMapper()).readValue(ids, ObjectId[].class);
		paymentService.deletePayment(idArray);
		return new ModelAndView("desktop/index").addObject("result",new Result("删除成功","success"));
	}
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable ObjectId id) throws JsonParseException, JsonMappingException, IOException{
		logger.debug("url:{}","/action/student/delete");
		logger.debug("id:{}",id);
		paymentService.deletePayment(id);
		return new ModelAndView("desktop/index").addObject("result",new Result("删除成功","success"));
	}
}
