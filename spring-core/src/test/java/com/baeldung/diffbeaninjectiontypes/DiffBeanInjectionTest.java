package com.baeldung.diffbeaninjectiontypes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DiffBeanInjectionTest {

	@Test
	public void givenAutowiredAnnotation_WhenSetOnProperty_ThenDependencyValid() {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);		
		CalculationPropertyInjection calculationPropertyInjection = context.getBean(CalculationPropertyInjection.class);
		Integer baseNumber = 10;

		assertTrue((baseNumber * baseNumber) == calculationPropertyInjection.calculate(baseNumber));
	}
	
	@Test
	public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		CalculationSetterInjection calculationSetterInjection = context.getBean(CalculationSetterInjection.class);
		Integer baseNumber = 10;
		
		assertTrue((baseNumber * baseNumber) == calculationSetterInjection.calculate(baseNumber));
	}
	
	@Test
	public void givenAutowiredAnnotation_WhenSetOnConstuctor_ThenDependencyValid() {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		CalculationConstructorInjection calculationConstructorInjection = context.getBean(CalculationConstructorInjection.class);
		Integer baseNumber = 10;
		
		assertTrue((baseNumber * baseNumber) == calculationConstructorInjection.calculate(baseNumber));
	}

}
