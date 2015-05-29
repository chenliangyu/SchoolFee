package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class PayAnalytics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String feeName;
	private ObjectId id;
	private Date startDate;
	private Date endDate;
	private List<PayAnalyticsForKlassAndSchool> result = new ArrayList<PayAnalyticsForKlassAndSchool>();
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<PayAnalyticsForKlassAndSchool> getResult() {
		return result;
	}
	public void setResult(List<PayAnalyticsForKlassAndSchool> result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "PayAnalytics [feeName=" + feeName + ", id=" + id
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", result=" + result + "]";
	}
}
