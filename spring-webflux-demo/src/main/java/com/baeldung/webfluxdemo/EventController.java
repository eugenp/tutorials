package com.baeldung.webfluxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class EventController {
	
	@Autowired
	EventService service;
	
	@RequestMapping(value="/events", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Event> test(){
		return service.getEvents();
	}
}
