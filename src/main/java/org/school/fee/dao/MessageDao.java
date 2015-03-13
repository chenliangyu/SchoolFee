package org.school.fee.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Message;

public interface MessageDao {
	public long countNewMessage(ObjectId userId);
	public void updateAllNewMessage(ObjectId userId);
}
