package com.example.ehidiamen.traindemo.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ehidiamen.traindemo.model.TrainStation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TrainRepository implements ReactiveCrudRepository {

	private final Map trainsMap = new ConcurrentHashMap();
	
	public TrainRepository() {
		trainsMap.put("1", new TrainStation("1", "Station1"));
		trainsMap.put("2", new TrainStation("2", "Station2"));
		trainsMap.put("3", new TrainStation("3", "Station3"));
		trainsMap.put("4", new TrainStation("4", "Station4"));
	}
	
	@Override
	public Mono count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono delete(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono deleteAll(Iterable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono deleteAll(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono deleteById(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono deleteById(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono existsById(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono existsById(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux findAllById(Iterable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux findAllById(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono findById(Object arg0) {
		return Mono.justOrEmpty(trainsMap.get(arg0));
	}

	@Override
	public Mono findById(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono save(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux saveAll(Iterable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux saveAll(Publisher arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
