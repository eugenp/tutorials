package demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.config.ResponseDataControllerAdvice;

/**
 * Maps URLs directly to views. Unlike View Controllers, using an actual
 * Controller causes the {@link ResponseDataControllerAdvice} to be invoked and
 * setup model data needed by these views.
 * 
 * @author Paul Chapman
 */
@Controller
public class ViewMappingController {

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/unannotated")
	public String unannotated() {
		return "unannotated";
	}

	@RequestMapping("/no-handler")
	public String noHandler() {
		return "no-handler";
	}
	
	@RequestMapping("/demo5")
	public String demo4() {
		return "demo5";
	}

}
