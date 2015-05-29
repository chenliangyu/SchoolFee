package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.support.enums.PayMethod;
import org.school.fee.support.enums.PayStatus;
import org.springframework.data.annotation.CreatedDate;
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
	private Date feeStartDate;
	private Date feeEndDate;
	private BigDecimal feeMoney;
	private String klass;
	private String school;
	private String phone;
	private List<PayRecord> money = new ArrayList<PayRecord>();
	private int payMethod;
	private int instalment;
	private Date expireDate;
	private int instalmentMethod;
	private Integer expireDayOfMonth;
	private Integer expireDayOfWeek;
	private Boolean sendNotify;
	private Boolean sendMessage;
	private int smsInterval;
	private int smsPeriod;
	private List<PayResult> payResults = new ArrayList<PayResult>();
	@CreatedDate
	private Date createDate;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/** copy student entity start  **/
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/** copy student entity end  **/
	
	
	
	/** copy of fee entity start **/
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
	public Date getFeeStartDate() {
		return feeStartDate;
	}
	public void setFeeStartDate(Date feeStartDate) {
		this.feeStartDate = feeStartDate;
	}
	public Date getFeeEndDate() {
		return feeEndDate;
	}
	public void setFeeEndDate(Date feeEndDate) {
		this.feeEndDate = feeEndDate;
	}
	/** copy of fee entity end **/
	
	public List<PayRecord> getMoney() {
		return money;
	}
	public void setMoney(List<PayRecord> money) {
		this.money = money;
	}
	
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	public List<PayResult> getPayResults() {
		return payResults;
	}
	public void setPayResults(List<PayResult> payResults) {
		this.payResults = payResults;
	}
	
	/** instalment properties start **/
	public int getInstalment() {
		return instalment;
	}
	public void setInstalment(int instalment) {
		this.instalment = instalment;
	}
	public Integer getExpireDayOfMonth() {
		return expireDayOfMonth;
	}
	public void setExpireDayOfMonth(Integer expireDayOfMonth) {
		this.expireDayOfMonth = expireDayOfMonth;
	}
	public Integer getExpireDayOfWeek() {
		return expireDayOfWeek;
	}
	public void setExpireDayOfWeek(Integer expireDayOfWeek) {
		this.expireDayOfWeek = expireDayOfWeek;
	}
	public int getInstalmentMethod() {
		return instalmentMethod;
	}
	public void setInstalmentMethod(int instalmentMethod) {
		this.instalmentMethod = instalmentMethod;
	}
	/** instalment properties end **/
	
	
	/** onepay properties start**/
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	/** onepay properties end**/
	
	/** notification and sms start **/
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
	public int getSmsInterval() {
		return smsInterval;
	}
	public void setSmsInterval(int smsInterval) {
		this.smsInterval = smsInterval;
	}
	public int getSmsPeriod() {
		return smsPeriod;
	}
	public void setSmsPeriod(int smsPeriod) {
		this.smsPeriod = smsPeriod;
	}
	/** notification and sms end **/
	
	
	/** created by mongodb start **/
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/** created by mongodb end **/
	
	
	/**  calculate properties start **/
	public BigDecimal getInstalmentMoney(){
		if(getPayMethod() == PayMethod.onePay.ordinal()){
			return BigDecimal.ZERO;
		}
		return getFeeMoney().divide(new BigDecimal(getInstalment()));
	}
	public BigDecimal getPayTotal(){
		BigDecimal total = BigDecimal.ZERO;
		if(getMoney().size()>0){
			List<PayRecord> payRecords = getMoney();
			for(PayRecord payRecord : payRecords){
				total = total.add(payRecord.getMoney());
			}
		}
		return total;
	}
	public Integer getRestInstalment(){
		int total = getInstalment();
		int hasPay = getPayResults().size();
		int notPay = total - hasPay;
		for(int i=hasPay-1;i>=0;i--){
			if(getPayResults().get(i).getStatus() == PayStatus.notClear.ordinal()){
				notPay++;
			}
		}
		return notPay;
	}
	public BigDecimal getNextNeedPay(){
		if(getPayMethod() == PayMethod.onePay.ordinal()){
			return BigDecimal.ZERO;
		}
		PayResult payResult = getPayResults().get(getPayResults().size() - 1);
		return payResult.getRestMoney();
	}
	/**  calculate properties end **/
	
	
	@Override
	public String toString() {
		return "Payment [id=" + id + ", studentId=" + studentId
				+ ", studentName=" + studentName + ", feeId=" + feeId
				+ ", feeName=" + feeName + ", feeStartDate=" + feeStartDate
				+ ", feeEndDate=" + feeEndDate + ", feeMoney=" + feeMoney
				+ ", klass=" + klass + ", school=" + school + ", phone="
				+ phone + ", money=" + money + ", payMethod=" + payMethod
				+ ", instalment=" + instalment + ", expireDate=" + expireDate
				+ ", instalmentMethod=" + instalmentMethod
				+ ", expireDayOfMonth=" + expireDayOfMonth
				+ ", expireDayOfWeek=" + expireDayOfWeek + ", sendNotify="
				+ sendNotify + ", sendMessage=" + sendMessage
				+ ", smsInterval=" + smsInterval + ", smsPeriod=" + smsPeriod
				+ ", payResults=" + payResults + ", createDate=" + createDate
				+ "]";
	}
}
