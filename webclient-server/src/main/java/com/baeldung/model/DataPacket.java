package com.baeldung.model;

import java.util.Date;

public class DataPacket {
	Date generatedDate;
	String data;
	
	public DataPacket() {
		super();
	}

	public DataPacket(Date generatedDate, String data) {
		super();
		this.generatedDate = generatedDate;
		this.data = data;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
