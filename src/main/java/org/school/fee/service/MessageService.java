package org.school.fee.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Message;
import org.school.fee.models.Payment;
import org.school.fee.models.Student;
import org.springframework.data.domain.Page;

public interface MessageService {
	public void sendMessage(Payment payment) throws IOException;
	public String getSMSTemplate();
	public void sendNotify(Student student,Payment payment) throws IOException;
	public void sendSMS(Student student,Payment payment);
	public Page<Message> listMessage(Integer page,Integer pageSize,ObjectId userId);
	public void deleteMessage(ObjectId id);
	public void deleteMessage(ObjectId[] id);
	public long countNewMessage(ObjectId userId);
	public void updateAllNewMessage(ObjectId userId);
}
