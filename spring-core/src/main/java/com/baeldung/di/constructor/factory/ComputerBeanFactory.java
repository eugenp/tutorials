package com.baeldung.di.constructor.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.constructor.config.SpringBeanConstructorContext;
import com.baeldung.di.constructor.model.bean.Computer;

public class ComputerBeanFactory {

	public static void main(String[] args) {
		// Create Spring Application Context
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringBeanConstructorContext.class);

		// Get Spring bean "computer" created
		Computer computer = (Computer) context.getBean("computer");
		System.out.println("---------------- Constructor Injection via @Bean ------------------");
		System.out.println("Processor cores : " + computer.getProcessor().getCores());
		System.out.println("Processor frequency : " + computer.getProcessor().getFrequency());
		System.out.println("Memory Size in GB : " + computer.getMemory().getSizeInGb());
		System.out.println("Memory format : " + computer.getMemory().getFormat());
		System.out.println("---------------- Constructor Injection via @Bean ------------------");

		// Close Spring Application Context
		((ConfigurableApplicationContext) context).close();
	}

}
