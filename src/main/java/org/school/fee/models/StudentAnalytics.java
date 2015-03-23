package org.school.fee.models;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class StudentAnalytics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String feename;
	private ObjectId feeId;
	private String klass;
	private String school;
	private int studentNumber;
	public String getFeename() {
		return feename;
	}
	public void setFeename(String feename) {
		this.feename = feename;
	}
	public ObjectId getFeeId() {
		return feeId;
	}
	public void setFeeId(ObjectId feeId) {
		this.feeId = feeId;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}
}
