package com.mauersu.util;

public enum RefreshModeEnum {
	auto("auto", "Auto refresh"),
	manually("manually", "Manually refresh"),
	;
	
	String name;
	String label;
	
	RefreshModeEnum(String name, String label) {
		this.name = name;
		this.label = label;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
