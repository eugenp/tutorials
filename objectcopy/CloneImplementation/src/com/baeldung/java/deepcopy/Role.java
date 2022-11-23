package com.baeldung.java.deepcopy;

public class Role implements Cloneable {
	
	public int level;
	public String designation;
	public int grossPay;
	
	Role(int level, String designation, int grossPay) {
		
		this.level = level;
		this.designation = designation;
		this.grossPay = grossPay;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
}
