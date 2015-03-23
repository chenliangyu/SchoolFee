package org.school.fee.service;


import org.school.fee.models.PayAnalytics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyticsService {
	public Page<PayAnalytics> analytics(Integer page,Integer pageSize);
}
