package org.baeldung.bean.injection;

import org.baeldung.bean.config.ConstructorBasedAppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConstructorBasedInjectionWithJavaConfigTest {

	private static final String WHEEL_NAME = "WheelBrand";

	@Test
	public void givenJavaConfigFile_whenUsingConstructorBasedBeanInjection_thenCorrectWheelName() {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ConstructorBasedAppConfig.class);
		context.refresh();

		Car car = context.getBean(Car.class);

		Assert.assertEquals(WHEEL_NAME, car.getWheel().getBrandOfWheel());
	}

}
