package org.school.fee.models;

import java.math.BigDecimal;
import java.util.Date;

public class SinglePay {
	private Date payDate;
	private BigDecimal money;
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
}
