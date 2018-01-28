package com.baeldung.spring.core.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		EmailServiceBean emailServiceBean = (EmailServiceBean) context.getBean("emailService");
		emailServiceBean.sendMessage();
		
		System.out.println();
		
		SMSServiceBean smsServiceBean = (SMSServiceBean) context.getBean("smsService");
		smsServiceBean.sendMessage();
	}

}
