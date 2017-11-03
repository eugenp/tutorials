package com.baeldung.onboarding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DependencyInjectionTest {

	@Autowired
	Person person;
	
	@Autowired
	Name name;
	
	@Test
    public void testValidNameBean() {
		assertNotNull(name);
		assertEquals(name.getFirstName(), "John");
		assertEquals(name.getLastName(), "Wick");
	}
	
	@Test
    public void testValidPersonBean() {
		assertNotNull(name);
		assertNotNull(person);
		assertEquals(person.getName().getFirstName(), "John");
		assertEquals(person.getName().getLastName(), "Wick");
	}
}