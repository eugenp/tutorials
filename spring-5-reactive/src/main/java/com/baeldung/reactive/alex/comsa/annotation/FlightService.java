package com.baeldung.reactive.alex.comsa.annotation;

import com.baeldung.reactive.alex.comsa.Plane;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
public class FlightService {

    private AtomicLong flightNumberGenerator = new AtomicLong();

    public Flux<Plane> getFlightsData() {

        Flux<Plane> flightData = Flux.fromStream(Stream.generate(() -> new Plane(
                getFlightNumber(),
                getDestionation(),
                getAirplaneType()
        )));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(flightData, interval).map(Tuple2::getT1);
    }


    private String getAirplaneType() {


        return RandomStringUtils.randomAlphabetic(5);
    }

    private String getDestionation() {
        return RandomStringUtils.randomAlphabetic(10);
    }


    private String getFlightNumber() {
        return Long.toHexString(flightNumberGenerator.incrementAndGet());

    }
}
