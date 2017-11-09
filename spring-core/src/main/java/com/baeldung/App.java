package com.baeldung.DIEval.DIEvaluation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.baeldung.DIEval.DIEvaluation");
		context.refresh();
		
		SetterInjectionCustomer cust = context.getBean(SetterInjectionCustomer.class);
		ConstructorInjectionCustomer customer =context.getBean(ConstructorInjectionCustomer.class);
		//String countryName = customer.fetchAddress("101");
		String countryName = cust.getCustCountry("101");
		
		context.close();
		
		System.out.println(countryName);
    }
}
