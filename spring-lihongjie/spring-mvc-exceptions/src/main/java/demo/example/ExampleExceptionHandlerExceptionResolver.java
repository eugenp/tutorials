package demo.example;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import demo.config.ExceptionConfiguration;

/**
 * Not used in this application, but an example of how to extend the
 * <tt>ExceptionHandlerExceptionResolver</tt> to provide extra information in the
 * model for the view. To use, add a @Bean method to
 * {@link ExceptionConfiguration} to return an instance.
 * 
 * @author Paul Chapman
 */
public class ExampleExceptionHandlerExceptionResolver extends
		ExceptionHandlerExceptionResolver {

	/**
	 * The default <tt>ExceptionHandlerExceptionResolver</tt> has order MAX_INT
	 * (lowest priority - see {@link Ordered#LOWEST_PRECEDENCE). The constructor
	 * gves this slightly higher precedence so it runs first. Also enable
	 * logging to this classe's logger by default.
	 */
	public ExampleExceptionHandlerExceptionResolver() {
		// Turn logging on by default
		setWarnLogCategory(getClass().getName());

		// Make sure this handler runs before the default
		// ExceptionHandlerExceptionResolver
		setOrder(LOWEST_PRECEDENCE - 1);
	}

	/**
	 * Override the default to generate a log message with dynamic content.
	 */
	@Override
	public String buildLogMessage(Exception e, HttpServletRequest req) {
		return "MVC exception: " + e.getLocalizedMessage();
	}

	/**
	 * This method uses the newee API and gets passed the handler-method
	 * (typically the method on the <tt>@Controller</tt>) that generated the
	 * exception.
	 */
	@Override
	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {

		// Get the ModelAndView to use
		ModelAndView mav = super.doResolveHandlerMethodException(request,
				response, handlerMethod, exception);

		// Make more information available to the view
		mav.addObject("exception", exception);
		mav.addObject("url", request.getRequestURL());
		mav.addObject("timestamp", new Date());
		mav.addObject("status", 500);
		return mav;
	}
}
