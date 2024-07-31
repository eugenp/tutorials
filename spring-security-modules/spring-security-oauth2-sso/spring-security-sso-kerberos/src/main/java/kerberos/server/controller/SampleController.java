package kerberos.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint")
class SampleController {

	@GetMapping
	String getIt() {
		return "data from kerberized server";
	}
}
