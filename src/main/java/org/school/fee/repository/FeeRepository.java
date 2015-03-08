package org.school.fee.repository;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeeRepository extends MongoRepository<Fee, ObjectId>{

}
