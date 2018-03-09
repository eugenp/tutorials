package demo3.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import demo.exceptions.CustomException;
import demo.exceptions.DatabaseException;
import demo.exceptions.InvalidCreditCardException;
import demo.exceptions.SupportInfoException;
import demo.exceptions.UnhandledException;

/**
 * A controller whose request-handler methods deliberately throw exceptions to
 * demonstrate the points discussed in the Blog.
 * <p>
 * Expects a <tt>SimpleMappingExceptionResolver</tt> to handle its exceptions.
 * 
 * @author Paul Chapman
 */
@Controller
@RequestMapping("/throw")
public class ExceptionThrowingController {

	protected Logger logger;
	protected SimpleMappingExceptionResolver resolver;

	public ExceptionThrowingController() {
		logger = LoggerFactory.getLogger(getClass());
	}

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
	/* . . . . . . . . . . . . . . REQUEST HANDLERS . . . . . . . . . . . . .. */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * Home page.
	 * 
	 * @return The view name (an HTML page with Thymeleaf markup).
	 */
	@RequestMapping("/")
	String home() {
		logger.info("Throw home page");

		// Check if the SwitchableSimpleMappingExceptionResolver is in use and
		// enabled, or not.
		if (resolver == null)
			return "no-handler";
		else if (resolver instanceof SwitchableSimpleMappingExceptionResolver) {
			return ((SwitchableSimpleMappingExceptionResolver) resolver)
					.isEnabled() ? "unannotated" : "no-handler";
		} else
			return "unannotated";
	}

	/**
	 * Simulates a database exception by always throwing
	 * <tt>DatabaseException</tt>. Handled by
	 * <tt>SimpleMappingExceptionResolver</tt>.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws DatabaseException
	 *             Always thrown.
	 */
	@RequestMapping("/databaseException")
	String throwDatabaseException() throws Exception {
		logger.info("Throw DatabaseException");
		throw new DatabaseException("Database not found: info.db");
	}

	/**
	 * Simulates an illegal credit-card exception by always throwing
	 * <tt>InvalidCreditCardException</tt>. Handled by
	 * <tt>SimpleMappingExceptionResolver</tt>.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws InvalidCreditCardException
	 *             Always thrown.
	 */
	@RequestMapping("/invalidCreditCard")
	String throwInvalidCreditCard() throws Exception {
		logger.info("Throw InvalidCreditCardException");
		throw new InvalidCreditCardException("1234123412341234");
	}

	/**
	 * Simulates a database exception by always throwing
	 * <tt>CustomException</tt>. Must be caught by an exception handler.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws CustomException
	 *             Always thrown.
	 */
	@RequestMapping("/supportInfoException")
	String throwCustomException() throws Exception {
		logger.info("Throw SupportInfoException");
		throw new SupportInfoException("Exception occurred");
	}

	/**
	 * Simulates a database exception by always throwing
	 * <tt>UnhandledException</tt>. Must be caught by an exception handler.
	 * 
	 * @return Nothing - it always throws the exception.
	 * @throws UnhandledException
	 *             Always thrown.
	 */
	@RequestMapping("/unhandledException")
	String throwUnhandledException() throws Exception {
		logger.info("Throw UnhandledException");
		throw new UnhandledException("Some exception occurred");
	}

}
