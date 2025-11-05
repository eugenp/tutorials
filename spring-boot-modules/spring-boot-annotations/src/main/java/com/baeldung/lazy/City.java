package com.baeldung.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class City {

    public City() {
        System.out.println("City bean initialized");
    }
}
