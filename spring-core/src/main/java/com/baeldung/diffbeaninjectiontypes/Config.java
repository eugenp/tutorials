package com.baeldung.diffbeaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	
	@Bean
    public CalculationConstructorInjection calculationConstructorInjection() {
		CalculationConstructorInjection calculationConstructorInjection = new CalculationConstructorInjection(new Calculation());
        return calculationConstructorInjection;
    }
	
	@Bean
	public CalculationPropertyInjection calculationPropertyInjection() {
		CalculationPropertyInjection calculationPropertyInjection = new CalculationPropertyInjection();
        return calculationPropertyInjection;
    }
	
	@Bean
	public CalculationSetterInjection calculationSetterInjection() {
		CalculationSetterInjection calculationSetterInjection = new CalculationSetterInjection();
        return calculationSetterInjection;
    }
	
	@Bean
	public Calculation calculation() {
		Calculation calculation = new Calculation();
        return calculation;
    }

}
