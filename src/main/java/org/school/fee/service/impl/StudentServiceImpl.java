package org.school.fee.service.impl;

import java.util.List;

import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.school.fee.repository.StudentRepository;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	StudentDao  studentDao;

	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentRepository.save(student);
	}

	public List<Student> getStudent(PaginationCriteria page, String keyword,
			Integer sex, Integer ageStart, Integer ageEnd, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		return studentDao.findStudent(page, keyword, sex, ageStart, ageEnd, orderBy, order);
	}

	public long countStudent(String keyword, Integer sex,
			Integer ageStart, Integer ageEnd) {
		// TODO Auto-generated method stub
		return studentDao.CountStudent(keyword, sex, ageStart, ageEnd);
	}
	
}
