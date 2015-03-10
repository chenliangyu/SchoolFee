package org.school.fee.controller;

import org.bson.types.ObjectId;
import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		Boolean isModify = student.getId() != null;
		if(isModify == null || !isModify){
			studentService.insertStudent(student);
		}else{
			studentService.saveStudent(student);
		}
		Result result = new Result("success",isModify);
		return new ModelAndView("desktop/addstudent").addObject("result", result);
	}
	@RequestMapping("/modify/{studentId}")
	public ModelAndView modify(ObjectId id){
		logger.debug("url:{}","/action/student/modify");
		logger.debug("id:{}",id);
		Student student = studentService.getStudent(id);
		return new ModelAndView("desktop/addstudent").addObject("result",new Result("success",student));
	}
	
	
	
	@RequestMapping("/delete")
	public ModelAndView delete(ObjectId[] ids){
		logger.debug("url:{}","/action/student/postadd");
		logger.debug("ids:{}",ids);
		studentService.deleteStudent(ids);
		return new ModelAndView("desktop/index").addObject("result",new Result("删除成功","success"));
	}
}
