package org.school.fee.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;

public interface FeeService {
	public void insertFee(Fee fee);
	public void saveFee(Fee fee);
	public List<Fee> listFee(String name);
	public void deleteFee(ObjectId id);
	public void deleteFee(ObjectId[] id);
}
