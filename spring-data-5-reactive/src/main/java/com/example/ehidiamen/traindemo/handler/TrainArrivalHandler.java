package com.example.ehidiamen.traindemo.handler;

import java.time.Duration;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.ehidiamen.traindemo.model.TrainArrival;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TrainArrivalHandler {

	public Mono arrivals(ServerRequest serverRequest) {
		String trainStationId = serverRequest.pathVariable("id");
		Flux events = Flux.generate(sink -> sink.next(new TrainArrival(trainStationId, new Date().toString()))).delayElements(Duration.ofSeconds(1));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(events, TrainArrival.class);
	}
}
