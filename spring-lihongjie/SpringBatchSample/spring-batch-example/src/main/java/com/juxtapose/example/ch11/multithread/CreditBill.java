/**
 * 
 */
package com.juxtapose.example.ch11.multithread;

import java.io.Serializable;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-11-17上午07:36:14
 */
public class CreditBill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4940717436114184875L;
	private String id;
	private String accountID = "";	/** 银行卡账户ID */
	private String name = "";		/** 持卡人姓名 */
	private double amount = 0;		/** 消费金额 */
	private String date;			/** 消费日期 ，格式YYYY-MM-DD HH:MM:SS*/
	private String address;			/** 消费场所 **/
	private String flag = "false";
	
	public CreditBill(){}
	
	public CreditBill(String accountID, String name, double amount, String date, String address){
		this.accountID = accountID;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.address = address;
	}
	
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


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("accountID=" + getAccountID() + ";name=" + getName() + ";amount="
				+ getAmount() + ";date=" + getDate() + ";address=" + getAddress());
		return sb.toString();
	}
}
