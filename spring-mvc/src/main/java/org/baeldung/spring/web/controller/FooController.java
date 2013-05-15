package org.baeldung.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FooController {

	public FooController() {
		super();
	}

	// API

	@RequestMapping(value = "/foos")
	public String getFoos() {
		System.out.println("simplePattern method was called");
		return "someResult";
	}

}
