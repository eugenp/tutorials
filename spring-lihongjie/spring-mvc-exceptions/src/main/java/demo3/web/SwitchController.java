package demo3.web;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.main.Main;

/**
 * A simple controller for enabling and disabling the
 * {@link SwitchableSimpleMappingExceptionResolver}.
 */
@Controller
@Profile("no-auto-creation")
public class SwitchController {

	protected SwitchableSimpleMappingExceptionResolver resolver;

	@Autowired
	public SwitchController(SwitchableSimpleMappingExceptionResolver resolver) {
		this.resolver = resolver;
	}

	/**
	 * Must return the currently active profiles for the home page (index.html)
	 * to use.
	 * 
	 * @return Currently active profiles.
	 */
	@ModelAttribute("profiles")
	public String getProfiles() {
		return Main.getProfiles();
	}

	/**
	 * Enable the profile if <code>action</code> is "on".
	 * 
	 * @param action
	 *            What to do. Resolver is enabled if set to "ON" (case ignored).
	 * @return Redirection back to the home page.
	 */
	@RequestMapping("/simpleMappingExceptionResolver/{action}")
	public String enable(@PathVariable("action") String action, Model model) {
		LoggerFactory.getLogger(getClass()).info(
				"SwitchableSimpleMappingExceptionResolver is " + action);
		// Anything other than ON, is treated as OFF
		boolean enabled = action.equalsIgnoreCase("on");
		resolver.setEnabled(enabled);

		// The ResponseDataControllerAdvice sets this for every request, but
		// before it is changed by this controller. So we need to set it
		// again - to reflect the new value.
		model.addAttribute("switchState", enabled ? "on" : "off");

		// Which view to return depends on resolver status
		// - Enabled? Show use of exception handing without annotations.
		// - Disabled? Show what happens with no excpetion handling at all.
		return enabled ? "unannotated" : "no-handler";
	}
}