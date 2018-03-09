package spring.scope.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.scope.bean.HelloMessageGenerator;

@Controller
public class ScopesController {

	@Autowired
	HelloMessageGenerator requestMessageGenerator;
	
	@RequestMapping(value="/scope")
	public String getScopes(Model model) {
		
		requestMessageGenerator.setMessage("Good morning");
		model.addAttribute("message", requestMessageGenerator.getMessage());
		return "message";
	}
	
	@RequestMapping(value="/hello")
	public String hello(Model model) {
		
		model.addAttribute("message", requestMessageGenerator.getMessage());
		return "hello";
	}
}
