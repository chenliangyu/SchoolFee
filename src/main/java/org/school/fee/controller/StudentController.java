package org.school.fee.controller;

import java.util.HashMap;
import java.util.Map;

import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.PaginationCriteria;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ModelAndView index(Integer page,String keyword,String searchField,String sort,Integer order){
		logger.debug("url:{}","/action/student/");
		return new ModelAndView("/desktop/index");
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
