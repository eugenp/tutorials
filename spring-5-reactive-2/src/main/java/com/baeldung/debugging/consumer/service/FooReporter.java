package com.baeldung.debugging.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.debugging.consumer.model.Foo;

import reactor.core.publisher.Flux;

public class FooReporter {

    private static Logger logger = LoggerFactory.getLogger(FooReporter.class);

    public static Flux<Foo> reportResult(Flux<Foo> input, String approach) {
        return input.map(foo -> {
            if (foo.getId() == null)
                throw new IllegalArgumentException("Null id is not valid!");
            logger.info("Reporting for approach {}: Foo with id '{}' name '{}' and quantity '{}'", approach, foo.getId(), foo.getFormattedName(), foo.getQuantity());
            return foo;
        });
    }

    public static Flux<Foo> reportResult(Flux<Foo> input) {
        return reportResult(input, "default");
    }
}
