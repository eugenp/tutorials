package com.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyAddress implements Cloneable {

	private long id;
	private String street;
	private String city;
	private String state;
	private String zipCode;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
