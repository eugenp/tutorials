package com.baeldung.flips.service;

import com.baeldung.flips.model.Foo;
import org.springframework.stereotype.Service;

@Service
public class NewFlipService {

    public Foo getNewFoo() {
        return new Foo("Shiny New Foo!", 100);
    }

}