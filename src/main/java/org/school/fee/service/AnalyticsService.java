package org.school.fee.service;


import java.util.Date;

import org.bson.types.ObjectId;
import org.school.fee.models.PayAnalytics;
import org.school.fee.support.enums.PayMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyticsService {
	public Page<PayAnalytics> analytics(Integer page,Integer pageSize,
			ObjectId feeId,String feeName,String studentName,String klass,String school,Boolean notClear,PayMethod payMethod,
			Date startDate,Date endDate);
}
