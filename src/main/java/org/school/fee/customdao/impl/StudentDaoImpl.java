package org.school.fee.customdao.impl;

import java.util.List;


import org.school.fee.customdao.CustomStudentDao;
import org.school.fee.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class StudentDaoImpl implements CustomStudentDao{
	
	@Autowired
	MongoTemplate mongoTemplate;

	public List<Student> findStudent(Pageable page, String keyword,
			Integer sex, Integer ageStart, Integer ageEnd, String orderBy,
			Integer order) {
		// TODO Auto-generated method stub
		MongoTemplate.find();
	}
}
