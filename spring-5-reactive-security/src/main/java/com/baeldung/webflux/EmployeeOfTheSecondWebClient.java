package com.baeldung.webflux;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class EmployeeOfTheSecondWebClient {

    WebClient client = WebClient.create("http://localhost:8080");
    
    public void consume() {

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