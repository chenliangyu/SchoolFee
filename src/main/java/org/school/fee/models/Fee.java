package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.school.fee.support.utils.ObjectIdSerializable;
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
	@Override
	public String toString() {
		return "Fee [id=" + id + ", name=" + name + ", money=" + money + "]";
	}
}
