package org.school.fee.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayAnalyticsForKlassAndSchool implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String klass;
	private String school;
	private int instalmentStudentNumber;
	private int onepayStudentNumber;
	private int instalmentNotClearStudentNumber;
	private int onepayNotClearStudentNumber;
	private BigDecimal instalmentTotal;
	private BigDecimal onepayTotal;
	private BigDecimal instalmentPayTotal;
	private BigDecimal onepayPayTotal;
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
	public int getInstalmentStudentNumber() {
		return instalmentStudentNumber;
	}
	public void setInstalmentStudentNumber(int instalmentStudentNumber) {
		this.instalmentStudentNumber = instalmentStudentNumber;
	}
	public int getOnepayStudentNumber() {
		return onepayStudentNumber;
	}
	public void setOnepayStudentNumber(int onepayStudentNumber) {
		this.onepayStudentNumber = onepayStudentNumber;
	}
	public int getInstalmentNotClearStudentNumber() {
		return instalmentNotClearStudentNumber;
	}
	public void setInstalmentNotClearStudentNumber(
			int instalmentNotClearStudentNumber) {
		this.instalmentNotClearStudentNumber = instalmentNotClearStudentNumber;
	}
	public int getOnepayNotClearStudentNumber() {
		return onepayNotClearStudentNumber;
	}
	public void setOnepayNotClearStudentNumber(int onepayNotClearStudentNumber) {
		this.onepayNotClearStudentNumber = onepayNotClearStudentNumber;
	}
	public BigDecimal getInstalmentTotal() {
		return instalmentTotal;
	}
	public void setInstalmentTotal(BigDecimal instalmentTotal) {
		this.instalmentTotal = instalmentTotal;
	}
	public BigDecimal getOnepayTotal() {
		return onepayTotal;
	}
	public void setOnepayTotal(BigDecimal onepayTotal) {
		this.onepayTotal = onepayTotal;
	}
	public BigDecimal getInstalmentPayTotal() {
		return instalmentPayTotal;
	}
	public void setInstalmentPayTotal(BigDecimal instalmentPayTotal) {
		this.instalmentPayTotal = instalmentPayTotal;
	}
	public BigDecimal getOnepayPayTotal() {
		return onepayPayTotal;
	}
	public void setOnepayPayTotal(BigDecimal onepayPayTotal) {
		this.onepayPayTotal = onepayPayTotal;
	}
	public BigDecimal getTotal(){
		return getOnepayTotal().add(getInstalmentTotal());
	}
	public BigDecimal getPayTotal(){
		return getOnepayPayTotal().add(getInstalmentPayTotal());
	}
	public BigDecimal getRest(){
		return getTotal().subtract(getPayTotal());
	}
	public BigDecimal getInstalmentRest(){
		return getInstalmentTotal().subtract(getInstalmentPayTotal());
	}
	public BigDecimal getOnepayRest(){
		return getOnepayTotal().subtract(getOnepayPayTotal());
	}
	public int getHasPayStudentNumber(){
		return getInstalmentStudentNumber() + getOnepayStudentNumber();
	}
	public int getNotClearStudentNumber(){
		return getInstalmentNotClearStudentNumber() + getOnepayNotClearStudentNumber();
	}
	public int getClearStudentNumber(){
		return getHasPayStudentNumber() - getNotClearStudentNumber();
	}
	public int getInstalmentClearStudentNumber(){
		return getInstalmentStudentNumber() - getInstalmentNotClearStudentNumber();
	}
	public int getOnepayClearStudentNumber(){
		return getOnepayStudentNumber() - getOnepayNotClearStudentNumber();
	}
}
