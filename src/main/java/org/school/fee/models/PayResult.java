package org.school.fee.models;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.school.fee.support.enums.PayStatus;
import org.springframework.data.annotation.Id;

public class PayResult {
	private BigDecimal payMoney;
	private Date expireDate;
	private int status;
	private Boolean hasNotified = false;
	private Date lastSMSSendDate;
	private BigDecimal money = BigDecimal.ZERO;
	
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Boolean getHasNotified() {
		return hasNotified;
	}
	public void setHasNotified(Boolean hasNotified) {
		this.hasNotified = hasNotified;
	}
	public Date getLastSMSSendDate() {
		return lastSMSSendDate;
	}
	public void setLastSMSSendDate(Date lastSMSSendDate) {
		this.lastSMSSendDate = lastSMSSendDate;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getRestMoney() {
		return payMoney.subtract(money);
	}
	@Override
	public String toString() {
		return "PayResult [payMoney=" + payMoney + ", expireDate=" + expireDate
				+ ", status=" + status + ", hasNotified=" + hasNotified
				+ ", lastSMSSendDate=" + lastSMSSendDate + ", money=" + money
				+ "]";
	}
	
}
