package com.springwebflux.controller;

import java.time.Duration;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * @author ranjeetkaur
 *
 */
@RestController
@RequestMapping(value = "/v1")
public class DiceController {

    private static Random random = new Random();

    @GetMapping("/dice")
    public Flux<Integer> rollDice() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(pulse -> {System.out.println(random.nextInt(5) + 1);return random.nextInt(5) + 1;});
    }
    
}
