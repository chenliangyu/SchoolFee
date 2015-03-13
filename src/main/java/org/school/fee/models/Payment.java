package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
	private List<SinglePay> money = new ArrayList<SinglePay>();
	private Date payDate;
	private Date expireDate;
	private Boolean sendNotify;
	private Boolean sendMessage;
	private Boolean notClear;
	private BigDecimal payTotal;
	
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
	public List<SinglePay> getMoney() {
		return money;
	}
	public void setMoney(List<SinglePay> money) {
		this.money = money;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void pay(BigDecimal money){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),0,0,0);
		SinglePay singlePay = new SinglePay();
		singlePay.setMoney(money);
		singlePay.setPayDate(c.getTime());
		setPayDate(c.getTime());
		this.money.add(singlePay);
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
	public Boolean getSendNotify() {
		return sendNotify;
	}
	public void setSendNotify(Boolean sendNotify) {
		this.sendNotify = sendNotify;
	}
	public Boolean getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(Boolean sendMessage) {
		this.sendMessage = sendMessage;
	}
	public Boolean getNotClear() {
		return notClear;
	}
	public void setNotClear(Boolean notClear) {
		this.notClear = notClear;
	}
	public BigDecimal getPayTotal() {
		return payTotal;
	}
	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", studentId=" + studentId
				+ ", studentName=" + studentName + ", feeId=" + feeId
				+ ", feeName=" + feeName + ", feeMoney=" + feeMoney
				+ ", klass=" + klass + ", school=" + school + ", money="
				+ money + ", payDate=" + payDate + ", expireDate=" + expireDate
				+ ", sendNotify=" + sendNotify + ", sendMessage=" + sendMessage
				+ ", notClear=" + notClear + ", payTotal=" + payTotal + "]";
	}
}
