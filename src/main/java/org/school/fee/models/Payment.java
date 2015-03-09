package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
	@DBRef
	private Fee fee;
	private int payMethod;
	private int instalmentNumber;
	private int instalmentPeriod;//0:月,1:年,2:周
	private int dayOfMonth;
	private int monthOfYear;
	private int dayOfWeek;//1-7：星期一-星期日
	private List<Double> payMoney = new ArrayList<Double>();
	@Transient
	private Double currentPayMoney;
	@Transient
	private Double restMoney;
	@Transient
	private int restInstalmentNumber;
	
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
	public Fee getFee() {
		return fee;
	}
	public void setFee(Fee fee) {
		this.fee = fee;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public int getInstalmentNumber() {
		return instalmentNumber;
	}
	public void setInstalmentNumber(int instalmentNumber) {
		this.instalmentNumber = instalmentNumber;
	}
	public int getInstalmentPeriod() {
		return instalmentPeriod;
	}
	public void setInstalmentPeriod(int instalmentPeriod) {
		this.instalmentPeriod = instalmentPeriod;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public int getMonthOfYear() {
		return monthOfYear;
	}
	public void setMonthOfYear(int monthOfYear) {
		this.monthOfYear = monthOfYear;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public List<Double> getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(List<Double> payMoney) {
		this.payMoney = payMoney;
	}
	public Double getCurrentPayMoney() {
		return currentPayMoney;
	}
	public void setCurrentPayMoney(Double currentPayMoney) {
		this.currentPayMoney = currentPayMoney;
	}
	public void pay(double money){
		payMoney.add(money);
	}
	public double getPayTotal(){
		double sum = 0;
		for(double money:payMoney){
			sum+=money;
		}
		return sum;
	}
	public double getRestMoney(){
		double sum = getPayTotal();
		restMoney = (fee.getMoney() - sum);
		return restMoney;
	}
	public void setRestMoney(double restMoney){
		this.restMoney = restMoney;
	}
	public int getRestInstalmentNumber(){
		return 0;
	}
	public double getCurrentRestMoney(){
		if(payMethod == 0){
			return getRestMoney();
		}else{
			double instalment = getInstalment();
			double sum = getPayTotal();
			return sum%instalment;
		}
	}
	public double getInstalment(){
		double total = fee.getMoney();
		double each = total/instalmentNumber;
		return Math.round(each * 10)/10;
	}
}
