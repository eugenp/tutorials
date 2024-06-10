package com.baeldung.object.copy.dto;

import java.util.ArrayList;

public class ObjectCreateRequestDto {

	private String sourceCartName;
	private ArrayList<String> sourceCartItems;
	
	public String getSourceCartName() {
		return sourceCartName;
	}
	public void setSourceCartName(String sourceCartName) {
		this.sourceCartName = sourceCartName;
	}
	public ArrayList<String> getSourceCartItems() {
		return sourceCartItems;
	}
	public void setSourceCartItems(ArrayList<String> sourceCartItems) {
		this.sourceCartItems = sourceCartItems;
	}
}
