package com.mkyong.common.controller;
 
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class HelloController {
 
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal ) {
 
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();*/
		
		/*User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name = user.getUsername();*/
		
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Hello World");
		return "hello";
 
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
 
		return "login";
 
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 
		//String errormessage = resources.getMessage("login.error", null, null);
		model.addAttribute("error", "true");
		return "login";
 
	}
	
}