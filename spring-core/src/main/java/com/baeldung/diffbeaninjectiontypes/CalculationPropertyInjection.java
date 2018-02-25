package com.baeldung.diffbeaninjectiontypes;


public class CalculationPropertyInjection {
	
	private Calculation calculation;
	
    public CalculationPropertyInjection(Calculation calculation) {
        this.calculation = calculation;
    }

	 public Integer format(int nmbr) {
	    return calculation.multipleWithTen(nmbr);
	} 
}
