package demo.main;

import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import demo.config.ExceptionConfiguration;
import demo1.web.ExceptionHandlingController;
import demo2.web.ControllerWithoutExceptionHandlers;
import demo2.web.GlobalExceptionHandlingControllerAdvice;
import demo3.config.DemoExceptionConfiguration;

/**
 * Spring Bean configuration profiles used in this application. Three profile
 * combinations are currently in use:
 * <ul>
 * <li><code>CONTROLLER_PROFILE</code> - creates a controller that also does its own
 * exception handling - see {@link ExceptionHandlingController}.
 * <li><code>GLOBAL_PROFILE</code> and <code>JAVA_CONFIG_PROFILE</code> - creates a
 * controller with no exception handlers. Instead exceptions are handled
 * globally - see {@link ControllerWithoutExceptionHandlers} and
 * {@link GlobalExceptionHandlingControllerAdvice}. A
 * {@link SimpleMappingExceptionResolver} is also created using Java Config -
 * see {@link ExceptionConfiguration}.
 * <li><code>GLOBAL_PROFILE</code> and <code>XML_CONFIG_PROFILE</code> - as previous but
 * the {@link SimpleMappingExceptionResolver} is configured using XML - see
 * <code>mvc-configuration.xml</code>
 * </ul>
 * The ability to switch between Java or XML configuration for the
 * <code>SimpleMappingExceptionResolver</code> is just for demonstration purposes.
 * In a real application use one or the other, but not both. You are very likely
 * to have an existing XML definition for this bean, if you have an existing
 * application, and there is no need to change it.
 *
 * @author Paul Chapman
 */
public interface Profiles {

	/**
	 * Java configuration profile - see {@link ExceptionConfiguration}. Value =
	 * <b>{@value} </b>
	 */
	public static final String JAVA_CONFIG_PROFILE = "java-config";

	/**
	 * XML configuration property - see <code>mvc-configuration.xml</code>. Value =
	 * <b>{@value} </b>
	 */
	public static final String XML_CONFIG_PROFILE = "xml-config";

	/**
	 * DEMO mode configuration property - see {@link DemoExceptionConfiguration}. Value =
	 * <b>{@value} </b>
	 */
	public static final String DEMO_CONFIG_PROFILE = "demo-config";

}
