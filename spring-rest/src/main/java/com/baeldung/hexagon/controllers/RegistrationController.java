package com.baeldung.hexagon.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagon.domain.model.User;
import com.baeldung.hexagon.domain.ports.IRegistrationService;

/*
 * EndPoint http://localhost:8082/spring-rest/customers
 * Sample Payload POST {"name":"BaeldunUser","mobileNumber":"+9662121212121"}
 */
@RestController
public class RegistrationController {
	@Autowired
	IRegistrationService registrationService;

	@Autowired
	ModelMapper modelMapper;

	@PostMapping(path = "/customers", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) throws Exception {
		User user = modelMapper.map(userRegistrationRequest, User.class);
		registrationService.registerUser(user);

	}

}
