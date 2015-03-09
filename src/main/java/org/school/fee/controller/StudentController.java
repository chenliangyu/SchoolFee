package org.school.fee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/")
	public ModelAndView index(Integer pageNo,Integer pageSize,String keyword,Integer ageStart,Integer ageEnd,Integer sex,String orderBy,String order){
		logger.debug("url:{}","/action/student/");
		Page<Student> students = studentService.getStudent(pageNo,pageSize,keyword, sex, ageStart, ageEnd, orderBy, order);
		return new ModelAndView("/desktop/index").addObject("result", students);
	}
	
	@RequestMapping("/add")
	public ModelAndView add(){
		logger.debug("url:{}","/action/student/add");
		return new ModelAndView("/desktop/addstudent");
	}
	
	@RequestMapping("/postadd")
	public ModelAndView postadd(Student student){
		logger.debug("url:{}","/action/student/postadd");
		logger.debug("student:{}",student);
		studentService.saveStudent(student);
		Result result = new Result("添加成功","success");
		return new ModelAndView("desktop/addstudent").addObject("result", result);
	}
}
