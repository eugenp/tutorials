package com.baeldung.reactive.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.reactive.model.FruitStatus;
import com.baeldung.reactive.service.FruitService;

import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/fruit")
public class FruitController {

    @Autowired
    public FruitService fruitService;

    @ResponseBody
    @GetMapping(value = "/{name}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FruitStatus> getFruitStatus(@PathVariable String name) {
        return this.fruitService.getStatus(name)
            .delayElements(Duration.ofSeconds(1));

    }

}
