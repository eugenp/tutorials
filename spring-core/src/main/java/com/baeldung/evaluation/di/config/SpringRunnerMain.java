package com.baeldung.evaluation.di.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.evaluation.di.example.PaymentService;

public class SpringRunnerMain {
	private static ApplicationContext context;

	public static void main(String[] args) {
		PaymentService paymentService = getPaymentServiceFromXml();
		paymentService.executePayment(100);

		paymentService = getPaymentServiceFromJavaConfig();
		paymentService.executePayment(100);
	}

	private static PaymentService getPaymentServiceFromJavaConfig() {
		context = new AnnotationConfigApplicationContext(Config.class);
		return context.getBean(PaymentService.class);
	}

	private static PaymentService getPaymentServiceFromXml() {
		context = new ClassPathXmlApplicationContext("di-config.xml");
		return context.getBean(PaymentService.class);
	}
}
