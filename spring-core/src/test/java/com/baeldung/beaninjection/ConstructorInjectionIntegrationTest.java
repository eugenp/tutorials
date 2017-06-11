package com.baeldung.beaninjection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.domain.Car;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
  classes = Config.class)
public class ConstructorInjectionIntegrationTest {

	private Car car;

	@Autowired
	void setCar(Car car) {
		this.car = car;
	}
	
    @Test
    public void givenAutowireAnnotation_WhenOnConstrutor_THEN_MUST_INJECT_Dependency() {
        assertNotNull(car);
        assertNotNull(car.getEngine());
    }
}
