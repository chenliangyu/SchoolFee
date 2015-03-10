package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private ObjectId id;
	private ObjectId studentId;
	private String studentName;
	private ObjectId feeId;
	private String feeName;
	private BigDecimal feeMoney;
	private String klass;
	private String school;
	private List<BigDecimal> money = new ArrayList<BigDecimal>();
	private Date payDate;
	private Date expireDate;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public ObjectId getStudentId() {
		return studentId;
	}
	public void setStudentId(ObjectId studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public ObjectId getFeeId() {
		return feeId;
	}
	public void setFeeId(ObjectId feeId) {
		this.feeId = feeId;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public BigDecimal getFeeMoney() {
		return feeMoney;
	}
	public void setFeeMoney(BigDecimal feeMoney) {
		this.feeMoney = feeMoney;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public List<BigDecimal> getMoney() {
		return money;
	}
	public void setMoney(List<BigDecimal> money) {
		this.money = money;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void pay(BigDecimal money){
		this.money.add(money);
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
	@Override
	public String toString() {
		return "Payment [id=" + id + ", studentId=" + studentId
				+ ", studentName=" + studentName + ", feeId=" + feeId
				+ ", feeName=" + feeName + ", feeMoney=" + feeMoney
				+ ", klass=" + klass + ", school=" + school + ", money="
				+ money + ", payDate=" + payDate + ", expireDate=" + expireDate
				+ "]";
	}
	
}
