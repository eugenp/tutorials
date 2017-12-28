package com.baeldung.dependency.injectiontypes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InjectionConfig.class)
public class DependencyInjectionIntegrationTests {

	@Autowired
	DaoFactoryWithConstructorInjection daoFactoryWithConstructorInjection;

	@Autowired
	DaoFactoryWithSetterInjection daoFactoryWithSetterInjection;

	@Autowired
	DaoFactoryWithFieldInjection daoFactoryWithFieldInjection;

	@Test
	public void givenAutowiredAnnotation_WhenConstructorInject_ThenDependencyValid() {
		Dao result = daoFactoryWithConstructorInjection.create(true);

		assertTrue("Incorrect Dao implementation returned for constructor injection", result instanceof DatabaseDao);
	}

	@Test
	public void givenAutowiredAnnotation_WhenSetterInject_ThenDependencyValid() {
		Dao result = daoFactoryWithSetterInjection.create(true);

		assertTrue("Incorrect Dao implementation returned for setter injection", result instanceof DatabaseDao);
	}

	@Test
	public void givenAutowiredAnnotation_WhenFieldInject_ThenDependencyValid() {
		Dao result = daoFactoryWithFieldInjection.create(true);

		assertTrue("Incorrect Dao implementation returned for field injection", result instanceof DatabaseDao);
	}

}
