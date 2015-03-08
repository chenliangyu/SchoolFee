package org.school.fee.controller;

import java.util.HashMap;
import java.util.List;
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
	public ModelAndView index(Integer pageNo,String keyword,Integer ageStart,Integer ageEnd,Integer sex,String orderBy,String order){
		logger.debug("url:{}","/action/student/");
		Map<String,Object> result = new HashMap<String,Object>();
		PaginationCriteria page = new PaginationCriteria();
		long count = studentService.countStudent(keyword, sex, ageStart, ageEnd);
		page.setPage(pageNo);
		List<Student> students = studentService.getStudent(page, keyword, sex, ageStart, ageEnd, orderBy, order);
		result.put("total", count);
		result.put("data", students);
		return new ModelAndView("/desktop/index").addObject("result", result);
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
