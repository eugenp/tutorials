package com.baeldung.autowiremultipleimplementations.candidates;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
@Qualifier("goodServiceA-custom-name")
@Order(3)
public class GoodServiceA implements GoodService {

    @Override
    public String getHelloMessage() {
        return "Hello from A!";
    }
}
