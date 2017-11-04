package com.baeldung.spring;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.beans.Car;
import com.baeldung.spring.beans.Truck;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class DependencyInjectionTechniquesTest {

	@Autowired
	private Car car;

	@Autowired
	private Truck truck;

	@Test
	public void givenAutowired_WhenSetOnField_ThenDependencyResolved() {
		assertNotNull(car);
		assertNotNull(truck);
		assertNotNull(car.tyre);
		assertNotNull(truck.getTyre());
		
		assert (car.tyre.getType()).equals("High Performance");
		assert (truck.getTyre().getType()).equals("High Performance");
	}
}
