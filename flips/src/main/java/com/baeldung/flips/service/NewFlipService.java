package com.baeldung.flips.service;

import com.baeldung.flips.model.Thing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewFlipService {

    public Thing getNewThing() {
        return new Thing("Shiny New Thing!", 100);
    }

}