package com.ticketapp.core.application.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ticketapp.core.api.objects.TicketDetails;

@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private int code;
	private Date creation;
	private int priority;
	private String description;

	@ManyToOne
	private Account account;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Translate the domain model to the API
	 * 
	 * @return the transfer object
	 */
	public TicketDetails toTicketDetails() {
		TicketDetails transferObject = new TicketDetails();

		transferObject.setCode(this.getCode());
		transferObject.setCreation(this.getCreation());
		transferObject.setPriority(this.getPriority());

		Account account = this.getAccount();
		if (account != null) {
			// Loading other domain model dependencies
			transferObject.setAccount(account.getLogin());
		}
		return transferObject;
	}

	/**
	 * Translate the ticket to the domain model
	 * 
	 * @param ticketDetails
	 * @return model
	 */
	public static Ticket fromTicketDetails(TicketDetails ticketDetails) {
		Ticket domain = new Ticket();
		domain.setCode(ticketDetails.getCode());
		domain.setCreation(ticketDetails.getCreation());
		domain.setPriority(ticketDetails.getPriority());
		return domain;
	}

	@Override
	public String toString() {
		return "Ticket [code=" + code + ", creation=" + creation + ", priority=" + priority + ", description="
				+ description + ", account=" + account + "]";
	}
}
