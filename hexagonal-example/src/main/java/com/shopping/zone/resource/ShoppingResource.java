package com.shopping.zone.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.zone.pojo.User;
import com.shopping.zone.service.ShoppingService;

@RestController
public class ShoppingResource {

	@Autowired
	ShoppingService shoppingService;
	
	@PostMapping("/register")
	public void registerUser(@RequestBody User user)
	{
		shoppingService.registerUser(user);
	}
}
