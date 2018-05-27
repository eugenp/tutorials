package com.baeldung.reactive.alex.comsa.annotation;


import com.baeldung.reactive.alex.comsa.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/annotation")
public class FlightController {


    @Autowired
    private FlightService flightService;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE, path = "/flight")
    public Flux<Plane> getFlux() {
        return flightService.getFlightsData();

    }

}
