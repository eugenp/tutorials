package com.baeldung.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.services.DictionaryAutowiredService;

public class AutowiredConfigurationMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("autowired-application-context.xml");
		DictionaryAutowiredService service = context.getBean(DictionaryAutowiredService.class);
		service.translate();
	}

}
