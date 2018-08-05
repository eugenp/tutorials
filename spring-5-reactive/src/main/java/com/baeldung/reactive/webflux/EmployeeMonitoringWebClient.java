package com.baeldung.reactive.webflux;

import com.baeldung.reactive.model.EmployeeEvent;
import org.springframework.web.reactive.function.client.WebClient;

class EmployeeMonitoringWebClient {

    void subscribe() {
        WebClient.create("http://localhost:8080")
            .get()
            .uri("/employee-monitoring/events")
            .retrieve()
            .bodyToFlux(EmployeeEvent.class)
            .subscribe(System.out::println);
    }

}
