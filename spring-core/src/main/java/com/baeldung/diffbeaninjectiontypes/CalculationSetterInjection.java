package com.baeldung.diffbeaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class CalculationSetterInjection {
	
	private Calculation calculation;

    @Autowired
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }
    
    public Integer calculate(int nmbr) {
	    return calculation.multipleWithSameNumber(nmbr);
	} 

}
