package com.hexagonal.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hexagonal.example.service.HexaService;

@RestController
public class HexaController {

	@Autowired
	HexaService service;

	@GetMapping("/add/{number}")
	public @ResponseBody Object addNumber(@PathVariable("number") int num) {
		service.add(num);
		return "added";
	}

	@GetMapping("/getnumberatindex/{index}")
	public @ResponseBody Object get(@PathVariable("index") int index) {
		return service.getNumber(index);
	}

}
