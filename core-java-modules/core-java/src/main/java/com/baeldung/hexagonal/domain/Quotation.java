package com.baeldung.hexagonal.domain;

public class Quotation {
	private long id;
	private String text;
	
	public Quotation(long id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String toString() {
		return "Quotation [id="+this.id+", text="+this.text+"]";
	}
}
