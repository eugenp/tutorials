package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.config.AppConfig;
import com.baeldung.beaninjectiontypes.model.Vehicle;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

public class BeanInjectionTypesTest {

	@Test
	public void givenXmlContextMetadata_WhenInjectedOnSetter_ThenBeanValid() {

		ApplicationContext context = new ClassPathXmlApplicationContext("beaninjectiontypes-ctx.xml");
		Vehicle vehicle = (Vehicle) context.getBean("vehicleWithSetterInjectionBean");

		assertNotNull(vehicle.getEngine());
	}

	@Test
	public void givenXmlContextMetadata_WhenInjectedOnConstructor_ThenBeanValid() {

		ApplicationContext context = new ClassPathXmlApplicationContext("beaninjectiontypes-ctx.xml");
		Vehicle vehicle = (Vehicle) context.getBean("vehicleWithConstructorInjectionBean");

		assertNotNull(vehicle.getEngine());
	}


	@Test
	public void givenConfigurationClassContextMetadata_WhenInjectedOnSetter_ThenBeanValid() {

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Vehicle vehicle = (Vehicle) context.getBean("vehicleWithSetterInjection");

		assertNotNull(vehicle.getEngine());
	}

	@Test
	public void givenConfigurationClassContextMetadata_WhenInjectedOnConstructor_ThenBeanValid() {

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Vehicle vehicle = (Vehicle) context.getBean("vehicleWithConstructorInjection");

		assertNotNull(vehicle.getEngine());
	}

}
