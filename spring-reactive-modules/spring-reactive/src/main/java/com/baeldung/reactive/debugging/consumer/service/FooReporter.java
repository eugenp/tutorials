package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooReporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooReporter.class);

    public static Foo reportResult(Foo foo, String approach) {
        if (foo.getId() == null) {
            throw new IllegalArgumentException("Null id is not valid!");
        }
        LOGGER.info("Reporting for approach {}: Foo with id '{}' name '{}' and quantity '{}'",
          approach, foo.getId(), foo.getFormattedName(), foo.getQuantity());

        return foo;
    }

    public static Foo reportResult(Foo input) {
        return reportResult(input, "default");
    }
}
