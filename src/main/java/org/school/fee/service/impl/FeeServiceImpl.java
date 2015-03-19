package org.school.fee.service.impl;


import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.models.Fee;
import org.school.fee.repository.FeeRepository;
import org.school.fee.service.FeeService;
import org.school.fee.support.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeeServiceImpl implements FeeService {
	
	@Autowired
	FeeRepository feeRepository;
	
	public void saveFee(Fee fee) {
		// TODO Auto-generated method stub
		feeRepository.save(fee);
	}

	public List<Fee> listFee(String name) {
		// TODO Auto-generated method stub
		if(name!=null){
			return feeRepository.findByNameLike(name);
		}else{
			return feeRepository.findAll();
		}
	}

	public void insertFee(Fee fee) {
		// TODO Auto-generated method stub
		feeRepository.insert(fee);
	}

	public void deleteFee(ObjectId id) {
		// TODO Auto-generated method stub
		feeRepository.delete(id);
	}

	public void deleteFee(ObjectId[] ids) {
		// TODO Auto-generated method stub
		for(ObjectId id : ids){
			deleteFee(id);
		}
	}

	public Fee getFee(ObjectId id) {
		// TODO Auto-generated method stub
		return feeRepository.findOne(id);
	}
	
	
}
