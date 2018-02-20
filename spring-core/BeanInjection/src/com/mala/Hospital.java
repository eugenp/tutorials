package com.mala;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mala.model.Patient;

public class Hospital {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("patient.xml");
		
		//Patient patient = (Patient)context.getBean("firstPatient");
		//System.out.println(patient);
		
		Patient patient = (Patient)context.getBean("secondPatient");
		System.out.println(patient);
		
		
				
			
	}

}
