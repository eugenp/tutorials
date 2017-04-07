/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.cloud.greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GreetingController {

	@Autowired
	private HelloWorldClient helloWorldClient;

	@RequestMapping(value = "/get-greeting", method = RequestMethod.GET)

	public String greeting(Model model) {

		model.addAttribute("greeting", helloWorldClient.HelloWorld());
		return "greeting-view";

	}

}
