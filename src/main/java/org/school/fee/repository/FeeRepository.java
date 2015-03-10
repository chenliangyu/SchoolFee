package org.school.fee.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeeRepository extends MongoRepository<Fee, ObjectId>{
	public List<Fee> findByNameLike(String name);
}
