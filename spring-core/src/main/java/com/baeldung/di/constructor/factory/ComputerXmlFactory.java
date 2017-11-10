package com.baeldung.di.constructor.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.di.constructor.model.xml.Computer;



public class ComputerXmlFactory {

	public static void main(String[] args) {
		// Create Spring Application Context
		ApplicationContext context = new ClassPathXmlApplicationContext("application-di-constructor-context.xml");

		// Get Spring bean "computer" created
		Computer computer = (Computer) context.getBean("computer");
		System.out.println("---------------- Constructor Injection via XML ------------------");
		System.out.println("Processor cores : " + computer.getProcessor().getCores());
		System.out.println("Processor frequency : " + computer.getProcessor().getFrequency());
		System.out.println("Memory Size in GB : " + computer.getMemory().getSizeInGb());
		System.out.println("Memory format : " + computer.getMemory().getFormat());
		System.out.println("---------------- Constructor Injection via XML ------------------");

		// Close Spring Application Context
		((ConfigurableApplicationContext) context).close();
		
	}

}
