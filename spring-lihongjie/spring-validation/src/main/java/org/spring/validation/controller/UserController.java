package org.spring.validation.controller;

import javax.validation.Valid;

import org.spring.validation.model.User;
import org.spring.validation.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	@Autowired
	private UserValidator userValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		
		return "index";
	}
	
	@RequestMapping(value = "/users/1", method = RequestMethod.GET)
	public String getUser(Model model) {
		
		model.addAttribute("user", buildUser());
		return "user-edit";
	}
	
	@RequestMapping(value = "/users/2", method = RequestMethod.GET)
	public String getUser2(Model model) {
		
		model.addAttribute("user", buildUser());
		return "user-edit2";
	}
	
	@RequestMapping(value = "/users/1", method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result) {
		
		if (result.hasErrors()) {
			
			return "user-edit";
		}
		System.out.println("Success update user by form!");
		return "index";
	}
	
	@RequestMapping(value = "/users/2", method = RequestMethod.POST)
	public String updateUserByAjax(@Valid User user, BindingResult result) {
		
		if (result.hasErrors()) {
			
			return "user-edit2";
		}
		System.out.println("Success update user by ajax!");
		return "user-edit2";
	}
	
	
	private User buildUser() {
		
		User user = new User();
		user.setId(1L);
		user.setUsername("lihongjie");
		return user;
	}

}
