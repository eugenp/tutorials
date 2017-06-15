package org.baeldung.bean.injection;

import org.baeldung.bean.config.SetterBasedAppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SetterBasedInjectionWithJavaConfigTest {

	private static final String WHEEL_NAME = "WheelBrand";

	@Test
	public void givenJavaConfigFile_whenUsingSetterBasedBeanInjection_thenCorrectWheelName() {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SetterBasedAppConfig.class);
		context.refresh();

		Car car = context.getBean(Car.class);

		Assert.assertEquals(WHEEL_NAME, car.getWheel().getBrandOfWheel());
	}

}
