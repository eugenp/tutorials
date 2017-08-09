package com.baeldung.injection.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.assertEquals;

import com.baeldung.injection.Config;
import com.baeldung.injection.Product;

public class TestMain {

	@Test
	public void testConstructorInjection(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		Product product = context.getBean(Product.class);
		assertEquals("1 Year Onsite Maintenance", product.getWarranty().getName());
		context.close();
	}
	
	@Test
	public void testSetterInjection(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		Product product = context.getBean(Product.class);
		assertEquals("Power Cable", product.getAccessories().getName());
		context.close();
	}
	
}
