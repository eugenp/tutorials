package com.spring.webflux.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.webflux.reactive.model.Vehicle;
import com.spring.webflux.reactive.repository.HighwayTraffic;

import reactor.core.publisher.Mono;

@Component
public class HighwayHandler {
    
    @Autowired
    HighwayTraffic highwayTraffic;
    
    public Mono<ServerResponse> vehicleDetected(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(highwayTraffic.flowTraffic(),Vehicle.class);
        
    }

}
