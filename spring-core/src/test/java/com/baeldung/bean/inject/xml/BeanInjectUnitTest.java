package com.baeldung.bean.inject.xml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/BeanInjectTest-context.xml" })
public class BeanInjectUnitTest {

	@Autowired
	public Car car;

	@Test
	public void whenBeanInjected_withConstructorBased_andDetailsTrue_thenPass() {
		// No need of AudioSystem been as it is a setter injection
		String details = "Wheel Type:Tube-less, Manufacturer:Michelin";
		assertTrue(details.equals(car.details()));
	}

}
