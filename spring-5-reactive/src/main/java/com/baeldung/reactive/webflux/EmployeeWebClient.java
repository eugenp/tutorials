package com.baeldung.reactive.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeWebClient {

    WebClient client = WebClient.create("http://localhost:8080");
    
    public void consume() {

        Mono<Employee> employeeMono = client.get()
            .uri("/employees/{id}", "1")
            .retrieve()
            .bodyToMono(Employee.class);

        employeeMono.subscribe(System.out::println);
        
        Flux<Employee> employeeFlux = client.get()
            .uri("/employees")
            .retrieve()
            .bodyToFlux(Employee.class);
        
        employeeFlux.subscribe(System.out::println);
        
        Flux<EmployeeEvent> employeeEvents = client.get()
        		.uri("/employees/{id}/track", "1")
        		.accept(MediaType.TEXT_EVENT_STREAM)
        		.exchange()
        		.flatMapMany(e -> e.bodyToFlux(EmployeeEvent.class));
        
        employeeEvents.subscribe(System.out::println);
     
    }
}