package com.baeldung.r2dbc.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.baeldung.r2dbc.model.Player;

import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {

    @Query("select id, name, age from player where name = $1")
    Flux<Player> findAllByName(String name);

    @Query("select * from player where age = $1")
    Flux<Player> findByAge(int age);
}