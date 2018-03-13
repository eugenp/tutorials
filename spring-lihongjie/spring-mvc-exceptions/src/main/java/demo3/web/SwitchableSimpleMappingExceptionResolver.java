package demo3.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * A sub-class of {@link SimpleMappingExceptionResolver} that can be turned on
 * and off for demonstration purposes (you wouldn't do this in a real
 * application).
 */
public class SwitchableSimpleMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	protected boolean enabled = false;

	public SwitchableSimpleMappingExceptionResolver(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Enabled or not?
	 * 
	 * @return Is enabled?
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Allow this resolver to be turned on and off whilst the application is
	 * running.
	 * 
	 * @param enabled
	 *            Set to enabled?
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Resolver only handles exceptions if enabled. Overrides method inherited
	 * from {@link AbstractHandlerExceptionResolver}
	 */
	@Override
	protected boolean shouldApplyTo(HttpServletRequest request, Object handler) {
		return enabled && super.shouldApplyTo(request, handler);
	}

}