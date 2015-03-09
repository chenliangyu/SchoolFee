package org.school.fee.service.impl;

import org.bson.types.ObjectId;
import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.school.fee.repository.FeeRepository;
import org.school.fee.repository.StudentRepository;
import org.school.fee.service.StudentService;
import org.school.fee.support.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	StudentDao  studentDao;
	@Autowired
	FeeRepository feeRepository;

	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentRepository.save(student);
	}
	public Page<Student> getStudent(Integer page, Integer pageSize,String keyword,
			Integer sex, Integer ageStart, Integer ageEnd, String orderBy,
			String order) {
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, orderBy, order);
		Page<Student> findStudent = studentDao.findStudent(pageable, keyword, sex, ageStart, ageEnd);
		return findStudent;
	}

	public void insertStudent(Student student) {
		// TODO Auto-generated method stub
		studentRepository.insert(student);
	}

	public void deleteStudent(ObjectId id) {
		// TODO Auto-generated method stub
		studentRepository.delete(id);
	}
	public void deleteStudent(ObjectId[] ids) {
		// TODO Auto-generated method stub
		for(ObjectId id:ids){
			deleteStudent(id);
		}
	}
	public void addFeeDesc(ObjectId studentId, ObjectId[] feeIds) {
		// TODO Auto-generated method stub
		Student student = studentRepository.findOne(studentId);
		for(ObjectId id:feeIds){
			student.addFee(feeRepository.findOne(id));
		}
		saveStudent(student);
	}
}
