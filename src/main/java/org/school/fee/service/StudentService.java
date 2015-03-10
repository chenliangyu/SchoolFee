package org.school.fee.service;


import org.bson.types.ObjectId;
import org.school.fee.models.Payment;
import org.school.fee.models.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
	public void insertStudent(Student student);
	public void saveStudent(Student student);
	public Page<Student> getStudent(Integer page,Integer pageSize,String keyword,Integer sex,Integer ageStart,Integer ageEnd,String orderBy,String order);
	public void deleteStudent(ObjectId id);
	public void deleteStudent(ObjectId[] ids);
	public Student getStudent(ObjectId studentId);
}
