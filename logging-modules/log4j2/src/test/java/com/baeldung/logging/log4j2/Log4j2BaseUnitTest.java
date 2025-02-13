package com.baeldung.logging.log4j2;

import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.jupiter.api.AfterAll;

import java.lang.reflect.Field;

public class Log4j2BaseUnitTest {
	@AfterAll
	static void tearDown() throws Exception {
		Field factories = ConfigurationFactory.class.getDeclaredField("factories");
		factories.setAccessible(true);
		factories.set(null, null);
		ConfigurationFactory.resetConfigurationFactory();
	}
}
