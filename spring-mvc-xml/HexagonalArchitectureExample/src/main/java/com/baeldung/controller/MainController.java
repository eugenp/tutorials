package com.baeldung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index(ModelMap map){
	
		return "index";
	}
	
	@GetMapping("/thyme")
	public String resolveThyme(ModelMap map){
		map.addAttribute("name", "Welcome to ThymeLeaf GUI!" );
		return "thyme";
	}
	
	
	@GetMapping("/home")
	public String resolveJsp(ModelMap map){
		map.addAttribute( "name", "This home page is rendered in Thymeleaf." );
		return "home";
	}
	

}
