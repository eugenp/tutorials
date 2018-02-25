package com.baeldung.diffbeaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class CalculationSetterInjection {
	
	private Calculation calculation;

    public CalculationSetterInjection(Calculation calculation) {
        this.calculation = calculation;
    }

    @Autowired
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }
    
    public Integer format(int nmbr) {
	    return calculation.multipleWithTen(nmbr);
	} 

}
