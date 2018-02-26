package com.baeldung.diffbeaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class CalculationConstructorInjection {
	
	private Calculation calculation;

	@Autowired
    public CalculationConstructorInjection(Calculation calculation) {
        this.calculation = calculation;
    }

	 public Integer calculate(int nmbr) {
	    return calculation.multipleWithSameNumber(nmbr);
	} 
}
