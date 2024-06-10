package com.baeldung.object.copy.dto;

import java.util.ArrayList;

public class ObjectCopyRequestDto {

	private String sourceCartName;
	private ArrayList<String> sourceCartItems;
	
	private String copyCartName;
	private ArrayList<String> copyCartItems;
	
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
	public String getCopyCartName() {
		return copyCartName;
	}
	public void setCopyCartName(String copyCartName) {
		this.copyCartName = copyCartName;
	}
	public ArrayList<String> getCopyCartItems() {
		return copyCartItems;
	}
	public void setCopyCartItems(ArrayList<String> copyCartItems) {
		this.copyCartItems = copyCartItems;
	}
}
