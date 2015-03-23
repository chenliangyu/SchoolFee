package org.school.fee.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class PayAnalytics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String feeName;
	private ObjectId id;
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
	/**
	 * @return the result
	 */
	public List<PayAnalyticsForKlassAndSchool> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<PayAnalyticsForKlassAndSchool> result) {
		this.result = result;
	}
}
