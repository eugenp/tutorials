package com.baeldung.reactivedebugging.server.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.baeldung.reactivedebugging.server.model.Foo;
import com.baeldung.reactivedebugging.server.repository.FooCrudRepository;

@Component
public class DbInitializer {
	
	@Autowired
	FooCrudRepository repository;
	
	@EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {
		repository.save(new Foo(1, "name1"));
		repository.save(new Foo(2, null));
	}

}
