package com.baeldung.reactive.client;

import org.springframework.web.reactive.function.client.WebClient;
import com.baeldung.reactive.model.CpuUsage;

import lombok.Setter;
import reactor.core.publisher.Flux;

public class CpuUsageClient {
    
    @Setter
    WebClient webClient = WebClient.create();

    public void runClient() {
        Flux<CpuUsage> cpuUsageFlux = webClient.get()
            .uri("http://localhost:8080/cpu-usage")
            .retrieve()
            .bodyToFlux(CpuUsage.class);

        cpuUsageFlux.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        CpuUsageClient cpuClient = new CpuUsageClient();
        cpuClient.runClient();
    }

}