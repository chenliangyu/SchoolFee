package org.school.fee.dao;

import java.util.List;

import org.school.fee.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentDao {
	Page<Student> findStudent(Pageable page,String keyword,Integer sex,Integer ageStart,Integer ageEnd);
}
