package demo.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import demo.main.Main;
import demo3.web.SwitchableSimpleMappingExceptionResolver;

/**
 * Adds useful data into to the model for every request. This is another use of
 * a Controller Advice (besides exception handling).
 * 
 * @author Paul Chapman
 *
 */
@ControllerAdvice
public class ResponseDataControllerAdvice {

	public static final String SOURCE_ON_GITHUB = //
	"https://github.com/paulc4/mvc-exceptions/blob/master/src/main";

	public static final String BLOG_URL = //
	"https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc";

	private SimpleMappingExceptionResolver resolver;

	/**
	 * Need to see if the {@link SwitchableSimpleMappingExceptionResolver} is
	 * being used and if so, is it enabled?
	 * 
	 * @param resolver
	 */
	@Autowired(required = false)
	public void setSimpleMappingExceptionResolver(
			SimpleMappingExceptionResolver resolver) {
		this.resolver = resolver;
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* . . . . . . . . . . . . . . MODEL ATTRIBUTES . . . . . . . . . . . . .. */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * What profile are we currently using?
	 * <p>
	 * Note that error views do not have automatically have access to the model,
	 * so they do not have access to model-attributes either.
	 * 
	 * @return Always includes "CONTROLLER".
	 */
	@ModelAttribute("profiles")
	public String getProfiles() {
		return Main.getProfiles();
	}

	/**
	 * Required for compatibility with Spring Boot.
	 * 
	 * @return Date and time of current request.
	 */
	@ModelAttribute("timestamp")
	public String getTimestamp() {
		return new Date().toString();
	}

	/**
	 * Do we have a {@link SwitchableSimpleMappingExceptionResolver} and if so,
	 * what state is it in?
	 * 
	 * @return Date and time of current request.
	 */
	@ModelAttribute("switchState")
	public String getSwitchState() {
		// Check if the SwitchableSimpleMappingExceptionResolver is in use and
		// enabled, or not.
		if (resolver == null)
			return "off";
		else if (resolver instanceof SwitchableSimpleMappingExceptionResolver) {
			return ((SwitchableSimpleMappingExceptionResolver) resolver)
					.isEnabled() ? "on" : "off";
		} else
			return "on";
	}

	/**
	 * URL of this source on github.
	 * 
	 * @return
	 */
	@ModelAttribute("gitHubSrc")
	public String getGitHubSrcURL() {
		return SOURCE_ON_GITHUB;
	}

	/**
	 * Add Blog URL to model for use in any web-page.
	 * 
	 * @return URL of the Spring IO Blog article this demo relates to.
	 */
	@ModelAttribute("blogUrl")
	public String getBlogUrl() {
		return BLOG_URL;
	}
}
