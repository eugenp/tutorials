package demo5.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.exceptions.OrderNotFoundException;

/**
 * Demonstrate the difference between returning the error view and redirecting
 * to the Spring Boot error page (which uses the same error view).
 * 
 * @author Paul Chapman
 *
 */
@Controller
public class ReturnOrRedirectController {

	enum Action {
		RETURN_ACTION, FORWARD_ACTION
	}

	@SuppressWarnings("serial")
	protected static class DemoException extends RuntimeException {
		final public Action action;

		public DemoException(Action action) {
			this.action = action;
		}
	}

	protected Logger logger;

	public ReturnOrRedirectController() {
		logger = LoggerFactory.getLogger(getClass());
	}

/**
	 * Throws a {@link DemoException} with the action "return" - see
	 * {@link 
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws OrderNotFoundException
	 *             Always thrown.
	 */
	@RequestMapping("/demo5/return")
	String throwDemoException1() {
		logger.info("Throw DemoException - ask hander to return 'error' view");
		throw new DemoException(Action.RETURN_ACTION);
	}

/**
	 * Throws a {@link DemoException} with the action "return" - see
	 * {@link 
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws OrderNotFoundException
	 *             Always thrown.
	 */
	@RequestMapping("/demo5/forward")
	String throwDemoException2() {
		logger.info("Throw DemoException - ask hander to return 'error' view");
		throw new DemoException(Action.FORWARD_ACTION);
	}

	/**
	 * Handle a {@link DemoException} by returning the "error" view by name or
	 * redirecting to the "/error" URL. The first shows the error page with no
	 * additional information (ExceptionResolvers by default don't provide any).
	 * The second forces a redirect via Spring Boot's internal
	 * BasicErrorController whose <code>@RequestMapping</code> method adds
	 * exception and other details into the Model for the error view to use.
	 * 
	 * @return Exception view.
	 */
	@ExceptionHandler
	public String handleDemoException(DemoException exception,
			HttpServletRequest req) {
		logger.error("Handle DemoExeption - action is " + exception.action);

		// Because we are handling the error, the server thinks everything is
		// OK, so the status is 200. So let's set it to something else.
		req.setAttribute("javax.servlet.error.status_code",
				HttpStatus.BAD_REQUEST.value());
		return exception.action == Action.RETURN_ACTION ? "error"
				: "forward:/error";
	}

}
