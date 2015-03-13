package org.school.fee.dao.impl;

import java.util.Date;
import java.util.List;










import org.bson.types.ObjectId;
import org.school.fee.dao.MessageDao;
import org.school.fee.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

@Repository
public class MessageDaoImpl implements MessageDao{

	@Autowired
	MongoTemplate mongoTemplate;

	public long countNewMessage(ObjectId userId) {
		// TODO Auto-generated method stub
		return mongoTemplate.count(query(where("userId").is(userId).and("isNew").is(true)), Message.class);
	}

	public void updateAllNewMessage(ObjectId userId) {
		// TODO Auto-generated method stub
		mongoTemplate.updateMulti(query(where("userId").is(userId).and("isNew").is(true)), update("isNew",false),Message.class);
	}

}
