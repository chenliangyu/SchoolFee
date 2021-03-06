package org.school.fee.service.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.school.fee.dao.AnalyticsDao;
import org.school.fee.models.PayAnalytics;
import org.school.fee.service.AnalyticsService;
import org.school.fee.support.enums.PayMethod;
import org.school.fee.support.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	AnalyticsDao analyticsDao;
	
	public Page<PayAnalytics> analytics(Integer page,Integer pageSize,ObjectId feeId,String feeName,
			String studentName,String klass,String school,Boolean notClear,PayMethod payMethod,
			Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		Pageable pageable = PageUtils.buildPageRequest(page, pageSize, "createDate", "desc");
		return analyticsDao.listAnalytics(pageable,feeId,feeName,studentName,klass,school,notClear,payMethod,
				startDate,endDate);
	}

}
