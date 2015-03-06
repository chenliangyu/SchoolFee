package org.school.fee.service.impl;

import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.school.fee.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentDao studentDao;

	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentDao.save(student);
	}

	public Page<Student> getStudent(Page<Student> page, String keyword,
			Integer sex, Integer ageStart, Integer ageEnd, String sort,
			Integer orderBy) {
		// TODO Auto-generated method stub
		int count = this.countStudent(keyword, sex, ageStart, ageEnd);
		page.setTotal(count);
		
		
		return null;
	}

	public int countStudent(String keyword, Integer sex,
			Integer ageStart, Integer ageEnd) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
