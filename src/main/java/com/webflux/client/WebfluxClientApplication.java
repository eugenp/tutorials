package com.webflux.client;


import java.util.Collections;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.server.Book;

@SpringBootApplication
public class WebfluxClientApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebfluxClientApplication.class)
		.properties(Collections.singletonMap("server.port", "8081"))
		.run(args);
		
		WebClient webClient = WebClient.create("http://localhost:8080/");
		
		while(true) {
		
			webClient.get()
					  .uri("/allBooks")
					  .accept(MediaType.TEXT_EVENT_STREAM)
					  .retrieve()
					  .bodyToFlux(Book.class)
					  .subscribe(book -> System.out.println(book.getTitle() + " ,Time: "+System.currentTimeMillis()));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
