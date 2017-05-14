package com.baeldung.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baeldung.services.DictionaryService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String... args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		DictionaryService service = context.getBean(DictionaryService.class);
		service.translate();
	}
}

