package com.school.fee.mongdb.test.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StudentServiceTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	StudentService studentService;
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void insertData(){
		Student student = new Student();
		student.setAge(10);
		student.setName("天天");
		student.setFatherName("张亮");
		student.setPhone("1234567");
		student.setSchool("补习班");
		student.setSex(0);
		mongoTemplate.save(student);
		
		student = new Student();
		student.setAge(5);
		student.setName("王诗龄");
		student.setMotherName("李湘");
		student.setSex(1);
		mongoTemplate.save(student);
		
		student = new Student();
		student.setAge(7);
		student.setName("多多");
		student.setSex(1);
		mongoTemplate.save(student);
		
		List<Student> students= mongoTemplate.findAll(Student.class);
		assertEquals(students.size(),3);
	}
	
	@After
	public void destoryData(){
		mongoTemplate.dropCollection(Student.class);
	}
	
	@Test
	public void TestCountStudent(){
		long count = studentService.countStudent(null,null,null,null);
		assertEquals(3, count);
		
		count = studentService.countStudent("王诗龄", null,null,null);
		assertEquals(1,count);
		
		count = studentService.countStudent("李", null, null, null);
		assertEquals(1,count);
		
		count = studentService.countStudent("12345", null, null, null);
		assertEquals(1,count);
		
		count = studentService.countStudent("李", 0, null, null);
		assertEquals(0,count);
		
		count = studentService.countStudent(null, 1, null, null);
		assertEquals(2,count);
		
		count = studentService.countStudent(null,null,7, 10);
		assertEquals(2,count);
	}
	
	@Test
	public void TestFindStudent(){
		PaginationCriteria page = new PaginationCriteria();
		page.setPage(1);
		List<Student> students= studentService.getStudent(page, null, null, null, null, null, null);
		assertEquals(3, students.size());
		
		students = studentService.getStudent(page,"王诗龄", null,null,null,null,null);
		assertEquals(1, students.size());
		
		students = studentService.getStudent(page,"李", null,null,null,null,null);
		assertEquals(1, students.size());
		
		students = studentService.getStudent(page,"12345", null, null, null,null,null);
		assertEquals(1,students.size());
		
		students = studentService.getStudent(page,"李", 0,null,null,null,null);
		assertEquals(0,students.size());
		
		students = studentService.getStudent(page,null, 1, null, null,null,null);
		assertEquals(2,students.size());
		
		students = studentService.getStudent(page,null,null,7, 10,null,null);
		assertEquals(2,students.size());
	}
}
