package org.school.fee.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.customdao.CustomStudentDao;
import org.school.fee.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StudentDao  extends MongoRepository<Student, ObjectId>,CustomStudentDao{
}
