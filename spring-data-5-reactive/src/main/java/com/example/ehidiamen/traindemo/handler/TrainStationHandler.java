package com.example.ehidiamen.traindemo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.ehidiamen.traindemo.model.TrainStation;
import com.example.ehidiamen.traindemo.repository.TrainRepository;

import reactor.core.publisher.Mono;

@Component
public class TrainStationHandler {

	private final TrainRepository trainRepository;
	
	public TrainStationHandler(TrainRepository trainRepository) {
		this.trainRepository = trainRepository;
	}
	
	public Mono getId(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");
		Mono trainStation = this.trainRepository.findById(id);
		return ServerResponse.ok().body(trainStation, TrainStation.class);
	}
}
