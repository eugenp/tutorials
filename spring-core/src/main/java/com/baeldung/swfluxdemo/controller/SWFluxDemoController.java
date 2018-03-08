package com.baeldung.swfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.swfluxdemo.service.SWFluxDemoService;
import reactor.core.publisher.Flux;

/**
 * @author Robert B. Andrews 1.0 3/7/18
 *
 */
@RestController
public class SWFluxDemoController {

	@Autowired
	SWFluxDemoService swFluxDemoService;

	// Messages are Sent to the client as Server Sent Events
	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public Flux<String> pushEventSignal() {
		return swFluxDemoService.getInfinityString();
	}

}
