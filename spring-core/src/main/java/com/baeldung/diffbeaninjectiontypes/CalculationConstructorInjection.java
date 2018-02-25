package com.baeldung.diffbeaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class CalculationConstructorInjection {
	
	private Calculation calculation;

	@Autowired
    public CalculationConstructorInjection(Calculation calculation) {
        this.calculation = calculation;
    }

	 public Integer format(int nmbr) {
	    return calculation.multipleWithTen(nmbr);
	} 
}
