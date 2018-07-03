package com.baeldung.webclientdemo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ClientConsumer {
	
	@RequestMapping("/events/consume")
	public String consume(){
		
		WebClient wc = WebClient.create("http://localhost:8090/events");
		wc.get().accept(MediaType.TEXT_EVENT_STREAM)
		.retrieve().bodyToFlux(Event.class).subscribe( t -> System.out.println(t));
		
		return "Done";
		
	}
	
}
