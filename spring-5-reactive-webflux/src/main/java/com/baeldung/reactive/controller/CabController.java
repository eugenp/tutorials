/**
 * 
 */
package com.baeldung.reactive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.model.CabLocation;
import com.baeldung.reactive.service.CabService;

import reactor.core.publisher.Flux;

/**
 * @author swpraman
 *
 */
@RestController
public class CabController {
    
    /**
     * Logger instance 
     */
    private static final Logger logger = LoggerFactory.getLogger(CabController.class);
    
    @Autowired
    private CabService cabService;
    
    // Server sends location of the cab at the interval of 1 sec
    @GetMapping(value = "cab/location/{cabId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<CabLocation> cabLocation(@PathVariable("cabId") String cabId) {
            logger.debug("Getting location stream for cabId: {}", cabId);
            return cabService.getLocation(cabId);
    }

}
