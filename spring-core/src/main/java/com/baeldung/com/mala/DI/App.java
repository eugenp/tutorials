package com.mala.DI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mala.DI.config.Config;
import com.mala.DI.model.Patient;

public class App 
{
    public static void main( String[] args ) {
    	
    	ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    	Patient patient = context.getBean(Patient.class);
    	System.out.println(patient.getMedicine());
    }
}
