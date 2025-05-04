package com.baeldung.logging.log4j2;

import java.lang.reflect.Field;

import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.AfterClass;

public class Log4j2BaseIntegrationTest {
	@AfterClass
	public static void tearDown() throws Exception {
		Field factories = ConfigurationFactory.class.getDeclaredField("factories");
		factories.setAccessible(true);
		factories.set(null, null);
		ConfigurationFactory.resetConfigurationFactory();

	}
}
