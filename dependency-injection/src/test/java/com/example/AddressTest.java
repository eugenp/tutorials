package com.example;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:core-config/spring-config.xml")
public class AddressTest {
	@Autowired
	ApplicationContext context; 
	@Test
	public void testSpringConfigXml_whenSetter_thenInjected() {
		Address address = context.getBean("address", Address.class);
		assertNotNull(address);
	}
	
	@Test
	public void testSpringConfigXml_whenConstructor_thenInjected() {
		Address address = context.getBean("address_constructor", Address.class);
		assertNotNull(address);
	}
	
	@Test
	public void testSpringConfigXml_whenConstructorName_thenInjected() {
		Address address = context.getBean("address_type", Address.class);
		assertNotNull(address);
	}
	
	
	@Test
	public void testSpringConfigXml_whenConstructorIndex_thenInjected() {
		Address address = context.getBean("address_index", Address.class);
		assertNotNull(address);
	}
}
