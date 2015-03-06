package org.school.fee.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, ObjectId>{
	public List<User> findByUsernameAndPassword(String username,String password);
}