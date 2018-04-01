package com.mauersu.util;

public enum ShowTypeEnum {
	show("show", "hide"),
	hide("hide", "show"),
	;
	
	String state;
	String change2;
	
	ShowTypeEnum(String state, String change2) {
		this.state = state;
		this.change2 = change2;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChange2() {
		return change2;
	}

	public void setChange2(String change2) {
		this.change2 = change2;
	}
	
}
