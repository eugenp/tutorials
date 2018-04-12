package com.baeldung.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.model.DataPacket;

import reactor.core.publisher.Flux;

public class StreamWebClient {
	 
    private static final Logger LOG = LoggerFactory.getLogger(StreamWebClient.class);
 
    public static void main(String[] args) throws InterruptedException {

        final Flux<DataPacket> stream = WebClient
                .create("http://localhost:8080")
                .get().uri("/stream")
                .retrieve()
                .bodyToFlux(DataPacket.class);
    
        stream.subscribe(data -> LOG.info("DataPacket retrieved: {}", 
        		data.getGeneratedDate() + " - " + data.getData()));

        TimeUnit.MINUTES.sleep(10);
    }
}