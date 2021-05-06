package com.baeldung.filtersinterceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloConroller {
	
	private Logger logger = LoggerFactory.getLogger(HelloConroller.class);
	
	@GetMapping("/hello")
	public String hello() {
		logger.info("Hello from the controller");
		return "hello";
	}
	
}
