package org.baeldung.spring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FooController {

	public FooController() {
		super();
	}

	// API

	@RequestMapping(value = "/foos")
	@ResponseBody
	public String getFoos() {
		return "Get some Foos";
	}

	@RequestMapping(value = "/foos", method = RequestMethod.POST)
	@ResponseBody
	public String getFoosPost() {
		return "Post some Foos";
	}

	@RequestMapping(value = "/foos", method = RequestMethod.POST, headers = "key=val")
	@ResponseBody
	public String getFoosWithHeader() {
		return "Get some Foos with Header";
	}

}
