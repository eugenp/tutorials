package com.ticketapp.core.api.objects;

import java.util.Date;

/**
 * Transfer object for domain model
 */
public class TicketDetails {

	private int code;
	private Date creation;
	private String account;
	private int priority;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
