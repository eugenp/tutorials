package com.baeldung.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

public class StatelessGenerate {

    Logger logger = LoggerFactory.getLogger(StatelessGenerate.class);

    public void statelessGenerate() {
        Flux<String> flux = Flux.generate((sink) -> {
            sink.next("hallo");
        });
        flux.subscribe(logger::info);
    }

    public static void main(String[] args) {
        StatelessGenerate ps = new StatelessGenerate();
        ps.statelessGenerate();
    }

}
