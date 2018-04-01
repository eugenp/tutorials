/**
 * 
 */
package com.juxtapose.example.ch07.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 信用卡对账单模型.<br>
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-8-17上午07:50:40
 */
@Entity
@Table(name="t_credit")
public class CreditBill {
	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="ACCOUNTID")
	private String accountID = "";	/** 银行卡账户ID */
	
	@Column(name="NAME")
	private String name = "";		/** 持卡人姓名 */
	
	@Column(name="AMOUNT")
	private double amount = 0;		/** 消费金额 */
	
	@Column(name="DATE")
	private String date;			/** 消费日期 ，格式YYYY-MM-DD HH:MM:SS*/
	
	@Column(name="ADDRESS")
	private String address;			/** 消费场所 **/
	
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id=" + getId()+ ";accountID=" + getAccountID() + ";name=" + getName() + ";amount="
				+ getAmount() + ";date=" + getDate() + ";address=" + getAddress());
		return sb.toString();
	}
}
