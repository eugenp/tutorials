/**
 * 
 */
package com.baledung.webflux.pricingsub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Bujji
 *
 */
@SpringBootApplication
public class ClientApp implements CommandLineRunner{

	public static void main(String args[]) {
		SpringApplication.run(ClientApp.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String url="http://localhost:8085";
		
		WebClient client = WebClient.builder().baseUrl(url).build();
		
		client.get()
		.uri("/prices")
		.retrieve()
		.bodyToFlux(String.class)
		.subscribe(s -> System.out.println(s) );
		
	}

}
