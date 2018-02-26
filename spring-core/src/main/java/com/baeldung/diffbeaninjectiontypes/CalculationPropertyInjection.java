package com.baeldung.diffbeaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class CalculationPropertyInjection {
	
	@Autowired
	private Calculation calculation;

	 public Integer calculate(int nmbr) {
	    return calculation.multipleWithSameNumber(nmbr);
	} 
}
