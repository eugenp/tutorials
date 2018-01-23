package com.baeldung.bean.inject.boot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeanInjectionSpringApplication.class)
public class BeanInjectionUnitTests {

	@Autowired
	public Car car;

	@Test
	public void whenBeanInjected_withConstructorBased_andDetailsTrue_thenPass() {
		// No need of VideoPlayer been as it is required 'false'
		String details = "Wheel Type:Tube-Less, Manufacturer:Michelin And Audio Brand Name:SoundLink, Manufacturer:Bose Automotive";
		assertTrue(details.equals(car.details()));
	}

}
