package com.ticketapp.core.api.events.ticket;

import com.ticketapp.core.api.events.CommandEvent;

public class DeleteTicketEvent extends CommandEvent {

	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
