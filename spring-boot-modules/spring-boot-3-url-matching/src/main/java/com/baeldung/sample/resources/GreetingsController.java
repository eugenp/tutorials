package com.baeldung.sample.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

	@GetMapping("/some/greeting")
    public String greeting() {
		return "Hello";
	}

	@GetMapping("/some/greeting/")
    public String greetingTrailingSlash() {
		return "Hello with slash";
	}

}
