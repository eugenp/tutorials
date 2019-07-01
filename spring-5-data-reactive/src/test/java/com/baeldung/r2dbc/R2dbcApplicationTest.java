package com.baeldung.r2dbc;


import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class R2dbcApplicationTest {


    @Autowired
    private PlayerRepository playerRepository;


    @Test
    @Order(1)
    public void whenDeleteAllThen0IsExpected() {

        Mono<Void> deleteAll = this.playerRepository.deleteAll();
        StepVerifier
                .create(deleteAll)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void whenInsertFiveThen5IsExpected() {
        Flux<Player> playerFlux = Flux.just("Kaka", "Messi", "Ronaldo", "CR7", "Romario")
                .map(name -> new Player(null, name))
                .flatMap(p -> this.playerRepository.save(p));
        StepVerifier
                .create(playerFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void whenListAllThen5IsExpected() {
        Flux<Player> allPlayers = this.playerRepository.findAll();
        StepVerifier.create(allPlayers)
                .expectNextCount(5)
                .verifyComplete();

    }

    @Test
    @Order(4)
    public void whenSearchForCR7ThenOneRowExpceted() {
        Flux<Player> cr7 = this.playerRepository.findAllByName("CR7");
        StepVerifier.create(cr7)
                .expectNextCount(1)
                .verifyComplete();

    }

}
