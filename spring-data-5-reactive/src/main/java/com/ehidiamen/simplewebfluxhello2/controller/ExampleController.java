package com.ehidiamen.simplewebfluxhello2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ehidiamen.simplewebfluxhello2.examples.Sir;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ExampleController {


   //Note Spring Boot 4.3+ autowires single constructors now

   @GetMapping("hello/{who}")
   public Mono<String> hello(@PathVariable String who) {
      return Mono.just(who)
                 .map(w -> "Hello " + w + "!");
   }
   
}
