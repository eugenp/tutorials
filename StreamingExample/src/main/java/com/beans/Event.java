package com.beans;

import java.util.Date;

public class Event {

	private long id;
	private Date date;

	public Event(long id, Date date) {
		this.setId(id);
		this.setDate(date);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
