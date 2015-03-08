package org.school.fee.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository  extends MongoRepository<Student, ObjectId>{
}
