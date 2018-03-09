package demo3.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import demo.main.Profiles;
import demo3.web.SwitchController;
import demo3.web.SwitchableSimpleMappingExceptionResolver;

/**
 * Setup for exception handling using a {@link SimpleMappingExceptionResolver}
 * sub-class that can be enabled or disabled programmatically.
 * <p>
 * The use of the "demo-config" profile here is for demonstration only. A real
 * application would either create a {@link SimpleMappingExceptionResolver} or
 * it wouldn't.
 * 
 * @author Paul Chapman
 */
@Configuration
@Profile(Profiles.DEMO_CONFIG_PROFILE)
public class DemoExceptionConfiguration {

	protected Logger logger;

	public DemoExceptionConfiguration() {
		logger = LoggerFactory.getLogger(getClass());
		logger.info("Creating DemoExceptionConfiguration");
	}

	/**
	 * Setup the switchable {@link SimpleMappingExceptionResolver} to provide
	 * useful defaults for logging and handling exceptions.
	 * 
	 * @return The new resolver
	 */
	@Bean(name = "simpleMappingExceptionResolver")
	public SwitchableSimpleMappingExceptionResolver createSwitchableSimpleMappingExceptionResolver() {
		logger.info("Creating SwitchableSimpleMappingExceptionResolver in disabled mode");

		// Turn exception resolving off to start
		boolean initialState = false;

		SwitchableSimpleMappingExceptionResolver resolver = new SwitchableSimpleMappingExceptionResolver(
				initialState);

		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseException");
		mappings.setProperty("InvalidCreditCardException", "creditCardError");

		resolver.setExceptionMappings(mappings); // None by default
		resolver.setExceptionAttribute("ex"); // Default is "exception"
		resolver.setWarnLogCategory("demo1.ExceptionLogger"); // No default

		// See comment in ExceptionConfiguration
		resolver.setDefaultErrorView("defaultErrorPage");
		return resolver;
	}

	/**
	 * Create the {@link SwitchController}.
	 */
	@Bean(name = "switchController")
	public SwitchController createSwitchController() {
		logger.info("Creating SwitchController");
		return new SwitchController(
				createSwitchableSimpleMappingExceptionResolver());
	}

}
