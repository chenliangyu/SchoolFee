package org.school.fee.dao;

import java.util.List;

import org.school.fee.models.Student;
import org.school.fee.support.utils.PaginationCriteria;

public interface StudentDao {
	public long CountStudent(String keyword,Integer sex,Integer ageStart,Integer ageEnd);
	List<Student> findStudent(PaginationCriteria page,String keyword,Integer sex,Integer ageStart,Integer ageEnd,String orderBy,String order);
}
