package com.baeldung.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Demonstrates the use of {@link Lazy} to defer bean initialization until
 * it is first requested.
 *
 * Lazy initialization can reduce application startup time and memory usage,
 * but it introduces a proxy layer that may delay error detection until runtime.
 */
@Lazy
@Component
public class City {

    public City() {
        System.out.println("City bean initialized");
    }
}
