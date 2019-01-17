package com.baeldung.hexagonal.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.hexagonal.models.User;
import com.baeldung.hexagonal.services.UserService;
import com.baeldung.hexagonal.viewports.adapters.UserInterfaceAdapter;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users/add-new")
	public String addNewUser(@Valid User user, BindingResult bindingResult) {
		UserInterfaceAdapter adapter = new UserInterfaceAdapter();
		userService.addUser(user, bindingResult, adapter);
		return adapter.getViewName();
	}
}
