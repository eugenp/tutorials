/**
 * 
 */
package com.mybatis3.domain;

import java.io.Serializable;


/**
 * @author Siva
 *
 */
public class PhoneNumber  implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String countryCode;
	private String stateCode;
	private String number;
	
	public PhoneNumber() {
	}

	public PhoneNumber(String countryCode, String stateCode, String number) {
		super();
		this.countryCode = countryCode;
		this.stateCode = stateCode;
		this.number = number;
	}

	public PhoneNumber(String string) {
		if(string != null){
			String[] parts = string.split("-");
			if(parts.length>0) this.countryCode=parts[0];
			if(parts.length>1) this.stateCode=parts[1];
			if(parts.length>2) this.number=parts[2];
			
		}
	}
	
	/*@Override
	public String toString() {
		return this.getAsString();
	}*/
	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAsString() {
		return countryCode+"-"+stateCode+"-"+number;
	}
	
}
