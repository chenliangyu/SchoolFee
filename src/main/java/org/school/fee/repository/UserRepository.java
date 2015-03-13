package org.school.fee.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{
	public List<User> findByUsernameAndPassword(String username,String password);
	public User findOneByRole(String role);
}