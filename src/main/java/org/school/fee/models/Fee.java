package org.school.fee.models;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Fee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private String name;
	private Double money;
	private Date payDate;
	private Boolean isRepeat;
	private int payYear;
	private int payMonth;
	private int payDay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Boolean getIsRepeat() {
		return isRepeat;
	}
	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
	public int getPayYear() {
		return payYear;
	}
	public void setPayYear(int payYear) {
		this.payYear = payYear;
	}
	public int getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(int payMonth) {
		this.payMonth = payMonth;
	}
	public int getPayDay() {
		return payDay;
	}
	public void setPayDay(int payDay) {
		this.payDay = payDay;
	}
	@Override
	public String toString() {
		return "Fee [id=" + id + ", name=" + name + ", money=" + money
				+ ", payDate=" + payDate + ", isRepeat=" + isRepeat
				+ ", payYear=" + payYear + ", payMonth=" + payMonth
				+ ", payDay=" + payDay + "]";
	}
}
