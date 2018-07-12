/**
 * 
 */
package com.baeldung.reactive.service;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.reactive.model.CabLocation;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

/**
 * @author swpraman
 *
 */
@Service
public class CabService {
    
    /**
     * Logger instance 
     */
    private static final Logger logger = LoggerFactory.getLogger(CabService.class);
    
    /**
     * getLocation service for cab
     * @param cabId
     * @return
     */
    public Flux<CabLocation> getLocation(String cabId) {
        
        // Create a flux to retrieve location
        Flux<CabLocation> locFlux = Flux.fromStream(Stream.generate(() -> retrieveNewLocation(cabId)));
        
        // Zip the flux with an interval flux
        return Flux.interval(Duration.ofSeconds(1))
                   .zipWith(locFlux)
                   .map(Tuple2::getT2);
    }

    /**
     * A random instance to create random location parameters
     */
    private Random random = new Random();

    /**
     * A Dummy method to return random location. 
     * In a real project it should retrieve the location from a database or any other data source.
     * @param cabId
     * @return
     */
    private CabLocation retrieveNewLocation(String cabId) {
        logger.debug("Retrieveing location for cab: {}", cabId);
        CabLocation location = new CabLocation();
        location.setCabId(cabId);
        location.setLatititude(random.nextDouble());
        location.setLongitude(random.nextDouble());
        return location;
    }
}
