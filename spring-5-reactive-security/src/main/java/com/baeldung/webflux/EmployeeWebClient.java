package com.baeldung.webflux;

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

        // Let the main thread sleep for 5 seconds
        // in order to make sure that the employees list has been displayed completely.
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("*********************************");
        System.out.println();
        System.out.println("The Employee Of The Second");
        System.out.println();

        Flux<Employee> employeeOfTheSecondFlux = client.get()
            .uri("/employees/current")
            .retrieve()
            .bodyToFlux(Employee.class);

        employeeOfTheSecondFlux.subscribe(System.out::println);
    }
}