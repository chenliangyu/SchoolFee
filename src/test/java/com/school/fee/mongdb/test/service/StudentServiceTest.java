package com.school.fee.mongdb.test.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.fee.models.Fee;
import org.school.fee.models.Student;
import org.school.fee.repository.FeeRepository;
import org.school.fee.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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
	public void testFindStudent(){
		Page<Student> students= studentService.getStudent(0,20,null, null, null, null, null, null);
		assertEquals(3, students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,"王诗龄", null,null,null,null,null);
		assertEquals(1, students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,"李", null,null,null,null,null);
		assertEquals(1, students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,"12345", null, null, null,null,null);
		assertEquals(1,students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,"李", 0,null,null,null,null);
		assertEquals(0,students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,null, 1, null, null,null,null);
		assertEquals(2,students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,null,null,7, 10,null,null);
		assertEquals(2,students.getNumberOfElements());
		
		students = studentService.getStudent(0,20,"天天",null,7, null,null,null);
		assertEquals(1,students.getNumberOfElements());
	}
	
	@Test
	public void testDeleteStudent(){
		List<Student> students = mongoTemplate.findAll(Student.class);
		for(Student student:students){
			studentService.deleteStudent(student.getId());
		}
		long total = mongoTemplate.count(null, Student.class);
		assertEquals(0,total);
	}
	
	@Test
	public void testAddStudent(){
		Student student = new Student();
		student.setAge(10);
		student.setName("天天");
		student.setFatherName("张亮");
		student.setPhone("1234567");
		student.setSchool("补习班");
		student.setSex(0);
		studentService.insertStudent(student);
		assertEquals(4,mongoTemplate.count(null, Student.class));
	}
	
	@Test
	public void testSaveStudent(){
		Student student = mongoTemplate.findOne(query(where("name").is("天天")),Student.class);
		student.setAge(12);
		studentService.saveStudent(student);
		Student studentAfterSave = mongoTemplate.findOne(query(where("name").is("天天")),Student.class);
		assertEquals(studentAfterSave.getAge(),12);
		
	}
}
