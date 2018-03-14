package com.baeldung.flips.service;

import com.baeldung.flips.model.Thing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlipService {


    private final List<Thing> things;

    public FlipService() {

         things = new ArrayList<>();

         things.add(new Thing("Thing1", 1));
        things.add(new Thing("Thing2", 2));
        things.add(new Thing("Thing3", 3));
        things.add(new Thing("Thing4", 4));
        things.add(new Thing("Thing5", 5));
        things.add(new Thing("Thing6", 6));

    }



    public List<Thing> getAllThings() {
        return things;
    }

    public Optional<Thing> getThingById(int id) {
        return things.stream().filter(thing -> (thing.getId() == id)).findFirst();
    }

    public Thing getNewThing() {
        return new Thing("New Thing!", 99);
    }

    public Thing getLastThing() {
        return things.get(things.size() -1);
    }

    public Thing getFirstThing() {
        return things.get(0);
    }

}