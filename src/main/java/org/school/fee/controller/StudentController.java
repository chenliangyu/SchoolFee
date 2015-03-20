package org.school.fee.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.PageUtils;
import org.school.fee.support.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/test/{pageNo}")
	public ModelAndView test(@PathVariable Integer pageNo,HttpServletRequest request){
		logger.debug("pageNo is {}",pageNo);
		Pageable pageRequest = PageUtils.buildPageRequest(pageNo, null, null, null);
		Map<String,Object> result = new HashMap<String,Object>();
		List<Object> content = new ArrayList<Object>();
		addData(content, 1000);
		Page<Object> page = new PageImpl<Object>(content, pageRequest, 1000);
		result.put("page", page);
		result.put("url", request.getContextPath()+"/action/student/test");
		return new ModelAndView("/test").addObject("result",result);
	}
	private void addData(List<Object> content,Integer total){
		for(int i=0;i<total;i++){
			content.add("测试内容"+i);
		}
	}
	
	@RequestMapping("/")
	public ModelAndView forward(){
		return new ModelAndView("forward:/action/student/0");
	}
	
	@RequestMapping("/{pageNo}")
	public ModelAndView index(@PathVariable Integer pageNo,Integer pageSize,String keyword,Integer ageStart,Integer ageEnd,Integer sex,String orderBy,String order){
		Map<String,Object> filter = new HashMap<String, Object>();
		Map<String,Object> result = new HashMap<String, Object>();
		if(keyword!=null && !keyword.equals("")){
			filter.put("keyword", keyword);
		}
		if(ageStart!=null){
			filter.put("ageStart", ageStart);
		}
		if(ageEnd!=null){
			filter.put("ageStart", ageEnd);
		}
		if(sex!=null){
			if(sex==-1){
				sex = null;
			}else{
				filter.put("sex", sex);
			}
		}
		if(filter.keySet().size()!=0){
			result.put("hasFilter", true);
			result.put("filter", filter);
		}
		logger.debug("url:{}","/action/student/"+pageNo);
		logger.debug("pageSize:{},keyword:{},ageStart:{},ageEnd:{},sex:{},orderBy:{},order:{}",
				new Object[]{pageSize,keyword,ageStart,ageEnd,sex,orderBy,order});
		Page<Student> students = studentService.getStudent(pageNo,pageSize,keyword, sex, ageStart, ageEnd, orderBy, order);
		result.put("students", students);
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
		Map<String,Object> result = new HashMap<String,Object>();
		Boolean isModify = student.getId() != null;
		logger.debug("isModify:{}",isModify);
		result.put("isModify", isModify);
		if(isModify == null || !isModify){
			studentService.insertStudent(student);
		}else{
			result.put("student",student);
			studentService.saveStudent(student);
		}
		return new ModelAndView("desktop/addstudent").addObject("result", result);
	}
	@RequestMapping("/modify/{id}")
	public ModelAndView modify(@PathVariable ObjectId id){
		logger.debug("url:{}","/action/student/modify");
		logger.debug("id:{}",id);
		Student student = studentService.getStudent(id);
		logger.debug("student:{}",student);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("student", student);
		return new ModelAndView("desktop/addstudent").addObject("result",result);
	}
	
	@RequestMapping("/delete/{studentId}")
	public ModelAndView delete(@PathVariable ObjectId studentId){
		logger.debug("url:{}","/action/student/delete");
		logger.debug("id:{}",studentId);
		studentService.deleteStudent(studentId);
		return new ModelAndView("redirect:/action/student/0").addObject("result",new Result("删除成功","success"));
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(String ids) throws JsonParseException, JsonMappingException, IOException{
		logger.debug("url:{}","/action/student/delete");
		logger.debug("ids:{}",ids);
		ObjectId[] idArray = (new ObjectMapper()).readValue(ids, ObjectId[].class);
		studentService.deleteStudent(idArray);
		return new ModelAndView("desktop/index").addObject("result",new Result("删除成功","success"));
	}
}
