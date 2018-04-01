package com.lihongjie.spring.security.docs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SpringSecurityDocsController {
	
	@RequestMapping(value = "/docs/spring-security/4.1.2.RELEASE/reference/html/", method = RequestMethod.GET)
	public String doc412RELEASE() {
	
		
		return "4.1.2.RELEASE";
		
	}

}
