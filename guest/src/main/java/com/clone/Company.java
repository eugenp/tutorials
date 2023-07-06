package com.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
public class Company implements Cloneable {

	private long id;
	private String name;
	private CompanyAddress companyAddress;
	
	
	//Shallow or default Cloning
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// Deep Cloning 
	
	/*
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Company cloned = (Company)super.clone();
		cloned.setCompanyAddress((CompanyAddress)cloned.getCompanyAddress().clone());
		return cloned;
	}
	*/

}
