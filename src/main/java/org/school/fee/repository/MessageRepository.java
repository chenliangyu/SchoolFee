package org.school.fee.repository;

import org.bson.types.ObjectId;
import org.school.fee.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message,ObjectId>{
	public Page<Message> findByUserId(ObjectId userId,Pageable page);
}
