package org.school.fee.customdao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.school.fee.models.Student;

public interface CustomStudentDao {
	List<Student> findStudent(Pageable page,String keyword,Integer sex,Integer ageStart,Integer ageEnd,String orderBy,Integer order);
}
