package com.baeldung.reactive.alex.comsa.functional;


import com.baeldung.reactive.alex.comsa.Plane;
import com.baeldung.reactive.alex.comsa.annotation.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlightHandler {

    @Autowired
    private FlightService flightService;

    public Mono<ServerResponse> getFlightData(ServerRequest serverRequest){


        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).
                body(flightService.getFlightsData(), Plane.class);
    }
}
