package com.baeldung.springdoc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

	@RequestMapping("/myproject")
	public String getRedirectUrl() {
		return "redirect:swagger-ui/";
	}
}