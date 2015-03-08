package org.school.fee.service.impl;

import org.school.fee.models.Fee;
import org.school.fee.repository.FeeRepository;
import org.school.fee.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;

public class FeeServiceImpl implements FeeService {
	
	@Autowired
	FeeRepository feeRepository;
	
	@Override
	public void saveFee(Fee fee) {
		// TODO Auto-generated method stub
		feeRepository.save(fee);
	}

}
