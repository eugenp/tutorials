package com.baeldung.spring.serverconfig;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/timeout")
public class TimeoutController {

    @GetMapping("/{timeout}")
    private Mono<String> timeout(@PathVariable int timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return Mono.just("OK");
    }
}
