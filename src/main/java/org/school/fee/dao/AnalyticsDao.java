package org.school.fee.dao;

import org.school.fee.models.PayAnalytics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyticsDao {
	public Page<PayAnalytics> listAnalytics(Pageable pageable);
}
