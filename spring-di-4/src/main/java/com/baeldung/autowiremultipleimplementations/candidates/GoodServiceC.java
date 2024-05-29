package com.baeldung.autowiremultipleimplementations.candidates;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Primary
@Service
@Profile("default")
@Order(1)
public class GoodServiceC implements GoodService {

    @Override
    public String getHelloMessage() {
        return "Hello from C!";
    }
}
