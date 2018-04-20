package com.webflux.server;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/book/{id}")
	Mono<Book> getABook(@PathVariable Long id){
		return bookRepository.findById(id);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value="/allBooks")
	Flux<Book> getAllBooks(){
		return bookRepository.findAll().delayElements(Duration.ofMillis(1000));
	}

}
