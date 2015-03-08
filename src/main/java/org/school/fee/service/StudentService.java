package org.school.fee.service;

import java.util.List;

import org.school.fee.models.Student;
import org.school.fee.support.utils.PaginationCriteria;
import org.springframework.data.domain.Page;

public interface StudentService {
	public void saveStudent(Student student);
	public List<Student> getStudent(PaginationCriteria page,String keyword,Integer sex,Integer ageStart,Integer ageEnd,String orderBy,String order);
	public long countStudent(String keyword,Integer sex,Integer ageStart,Integer ageEnd);
}
