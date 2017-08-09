package com.baeldung.injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRun {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		Product product = context.getBean(Product.class);
		System.out.println(product.getAccessories().getName());
		context.close();
	}
	
}
