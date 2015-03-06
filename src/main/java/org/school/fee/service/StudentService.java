package org.school.fee.service;

import org.school.fee.models.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
	public void saveStudent(Student student);
	public Page<Student> getStudent(Page<Student> page,String keyword,Integer sex,Integer ageStart,Integer ageEnd,String sort,Integer orderBy);
	public int countStudent(String keyword,Integer sex,Integer ageStart,Integer ageEnd);
}
