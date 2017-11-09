package com.baeldung.DIEval.DIEvaluation;

import org.springframework.stereotype.Component;

@Component
public class Address {
	
	private String StreetName;
	private String cityName;
	private String ZipCode;
	private String countryName;
	
	public String fetchCountryName(String custID) {
		
		if(custID.equals("101")) {
			countryName="USA";
		}
		else if (custID.equals("201")) {
			countryName="India";
		}
		else {
			countryName="Antarctica";
		}
		return countryName;
	}

}

