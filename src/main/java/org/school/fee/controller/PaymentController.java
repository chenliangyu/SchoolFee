package org.school.fee.controller;

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
		result.put("student", student);
		result.put("payments", payments);
		result.put("feeList", feeList);
		return new ModelAndView("/payment/index").addObject("result",result);
	}
	@RequestMapping("/add")
	public ModelAndView add(Payment payment,BigDecimal money){
		logger.debug("uri:{}","/action/payment/add");
		logger.debug("payment:{}",payment);
		paymentService.addPayment(payment);
		if(money!=null && money.compareTo(BigDecimal.ZERO)>0){
			paymentService.pay(payment, money);
			//paymentService.pay(payment, money);
		}
		return new ModelAndView("/payment/index").addObject("result", new Result("添加成功","success"));
	}
	
	@RequestMapping("/pay/{paymentId}")
	public ModelAndView pay(ObjectId paymentId,BigDecimal money){
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
		result.put("payments", payments);
		return new ModelAndView("/payment/index").addObject("result",result);
	}
}
