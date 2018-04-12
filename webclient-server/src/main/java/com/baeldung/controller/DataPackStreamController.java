package com.baeldung.controller;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.DataPacket;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/stream")
public class DataPackStreamController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataPackStreamController.class);
	
    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<DataPacket> generateDataPacketEvents(){
    	LOG.info("DataPackStreamController :: generateDataPacketEvents : invoked");
    	Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
    	Flux<DataPacket> dataPacket = Flux.fromStream(
    			Stream.generate(() -> new DataPacket(new Date(), 
    					String.valueOf(Math.abs(new Random().nextLong())) )));
        return Flux.zip(interval, dataPacket).map(Tuple2::getT2);
    }
}
