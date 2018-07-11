package com.baeldung;

import com.baeldung.data.MemoryStats;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Scanner;

/**
 * Application.
 */
public class Application {

    // Web Client pulls data from Server API and prints in the console. By entering the character 'c', the application can be stopped.
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");

        Flux<MemoryStats> memoryStatsFlux = webClient.get().uri("/api/memoryStats")
                .retrieve().bodyToFlux(MemoryStats.class);

        memoryStatsFlux.subscribe(memoryStats -> System.out.println(memoryStats));

        Scanner scanner = new Scanner(System.in);
        while(!scanner.nextLine().equals("c")) {

        }
    }
}
