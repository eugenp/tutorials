package demo.main;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import demo.config.ExceptionConfiguration;

/**
 * Main entry point for our application using Spring Boot. It can be run as an
 * executable or as a standard war file.
 * <p>
 * Details of annotations used:
 * <ul>
 * <li><code>@EnableAutoConfiguration</code>: makes Spring Boot setup its
 * defaults.
 * <li><code>@ComponentScan</code>: Scan for @Component classes, including @Configuration
 * classes.
 * <li><code>@ImportResource</code>: Import Spring XML configuration file(s).
 * </ul>
 * 
 * @author Paul Chapman
 */
@EnableAutoConfiguration
@ComponentScan({ "demo", "demo1", "demo2", "demo3", "demo5" })
@ImportResource("classpath:mvc-configuration.xml")
public class Main extends SpringBootServletInitializer {

	/**
	 * How should a <code>SimpleMappingExceptionResolver</code> be created?
	 * <ul>
	 * <li>DEMO (default) - Java Config is used and a custom
	 * <code>SimpleMappingExceptionResolver</code> is setup that can be enabled
	 * or disabled programmatically (just for the purpose of this demo).
	 * <li>XML - Traditional XML bean configuration is used - see
	 * <code>mvc-configuration.xml</code>.
	 * <li>JAVA - Java Configuration is used - see
	 * {@link ExceptionConfiguration}.
	 * <li>NONE - No <code>SimpleMappingExceptionResolver</code> is created.
	 * </ul>
	 * <p>
	 * Demo mode is the default - set to "java-config" or "xml-config" to match
	 * however you intend to use Spring for a mor realistic setup.
	 * 
	 * @see Profiles
	 */
	public static final String activeProfile = Profiles.DEMO_CONFIG_PROFILE;

	// Local logger
	protected Logger logger;

	// Holds Spring Boot configuration properties
	protected Properties props = new Properties();

	/**
	 * Retrieve requested Profiles. Depends on the value of
	 * {@link #activeProfile}.
	 * 
	 * @return Comma-separated list of profiles.
	 */
	public static String getProfiles() {
		return activeProfile;
	}

	/**
	 * We are using the constructor to perform some useful initializations:
	 * <ol>
	 * <li>Set the Spring Profile to use 'controller' or 'global' which in turn
	 * selects how exceptions will be handled. Profiles are a Spring feature
	 * from V3.1 onwards.
	 * <li>Disable Thymeleaf caching so templates (HTML files with Thymeleaf
	 * namespace attributes) can be modified whilst the application is running.</li>
	 * <li>Enable DEBUG level logging so you can see Spring MVC as its working.</li>
	 * </ol>
	 */
	public Main() {

		logger = LoggerFactory.getLogger(getClass());
		logger.info("Application starting ");

		// These properties could alternatively be set in application.properties
		
		// Disable caching - during development if a page is changed, the
		// changes can be seen next time it is rendered. Should be 'true' in
		// production for efficiency.
		props.setProperty("spring.thymeleaf.cache", "false");

		// Spring boot assumes the fallback error page maps to /error. Set this
		// property to specify an alternative mapping. If using a
		// SimpleMappingExceptionResolver, make sure it's defaultErrorView
		// corresponds to the same page (see ErrorMvcAutoConfiguration).
		props.setProperty("error.path", "/error");

		// Set to false to turn-off Spring Boot's error page. Unhandled
		// exceptions will be handled by container in the usual way.
		props.setProperty("error.whitelabel.enabled", "true");

		// Enable internal logging for Spring MVC
		props.setProperty("org.springframework.web", "DEBUG");
	}

	/**
	 * Back to the future: run the application as a Java application and it will
	 * pick up a container (Tomcat, Jetty) automatically if present. Pulls in
	 * Tomcat by default, running in embedded mode.
	 * <p>
	 * This application can also run as a traditional war file because it
	 * extends <code>SpringBootServletInitializer</code> as well.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Create an instance and invoke run(); Allows the contructor to perform
		// initialisation regardless of whether we are running as an application
		// or in a container.
		new Main().runAsJavaApplication(args);
	}

	/**
	 * Run the application using Spring Boot. <code>SpringApplication.run</code>
	 * tells Spring Boot to use this class as the initialiser for the whole
	 * application (via the class annotations above). This method is only used
	 * when running as a Java application.
	 * 
	 * @param args
	 *            Any command line arguments.
	 */
	protected void runAsJavaApplication(String[] args) {
		SpringApplicationBuilder application = new SpringApplicationBuilder();
		configure(application);
		application.run(args);
		logger.info("Go to this URL: http://localhost:8080/");
	}

	/**
	 * Configure the application using the supplied builder when running as a
	 * WAR. This method is invoked automatically when running in a container and
	 * explicitly by {@link #runAsJavaApplication(String[])}.
	 * 
	 * @param application
	 *            Spring Boot application builder.
	 */
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		application.sources(Main.class);

		logger.info("Spring Boot configuration: profiles = " + activeProfile);
		application.profiles(activeProfile);

		// Set additional properties.
		logger.info("Spring Boot configuratoon: properties = " + props);
		application.properties(props);

		return application;
	}
}
