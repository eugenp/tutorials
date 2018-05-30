package com.baeldung.springbootnonwebapp;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposing rest web services
 * @author hemant
 *
 */
@RestController
public class HelloController {

	@RequestMapping("/")
	public LocalDate getMinLocalDate() {
		return LocalDate.MIN;
	}
}
