package com.hexagonal.arch.examples.core.domain;

public class CustomerCriteria {
	
    private String firstName;
    private String lastName;
    private int lastFourDigitsOfSSN;
    private String birthDate;
    
    
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getLastFourDigitsOfSSN() {
		return lastFourDigitsOfSSN;
	}
	public void setLastFourDigitsOfSSN(int lastFourDigitsOfSSN) {
		this.lastFourDigitsOfSSN = lastFourDigitsOfSSN;
	}

    
    
    

}
