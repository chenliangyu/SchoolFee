package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.school.fee.support.utils.ObjectIdSerializable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document
public class Fee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@JsonSerialize(using = ObjectIdSerializable.class)  
	private ObjectId id;
	private String name;
	private BigDecimal money;
	private Date startDate;
	private Date endDate;
	@CreatedDate
	private Date createDate;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Fee [id=" + id + ", name=" + name + ", money=" + money
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
