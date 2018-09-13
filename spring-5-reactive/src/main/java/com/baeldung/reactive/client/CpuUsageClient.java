package com.baeldung.reactive.client;

import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.CpuUsage;

import reactor.core.publisher.Flux;

public class CpuUsageClient {

    public void runClient() {
        WebClient client = WebClient.create("http://localhost:8080");

        Flux<CpuUsage> cpuUsageFlux = client.get()
            .uri("/cpu-usage")
            .retrieve()
            .bodyToFlux(CpuUsage.class);

        cpuUsageFlux.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        CpuUsageClient cpuClient = new CpuUsageClient();
        cpuClient.runClient();
    }

}
