package org.school.fee.service;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.springframework.data.domain.Page;

public interface FeeService {
	public void insertFee(Fee fee);
	public void saveFee(Fee fee);
	public Page<Fee> listFee(Integer page,Integer pageSize,String name,String orderBy,String order);
	public void deleteFee(ObjectId id);
	public void deleteFee(ObjectId[] id);
}
