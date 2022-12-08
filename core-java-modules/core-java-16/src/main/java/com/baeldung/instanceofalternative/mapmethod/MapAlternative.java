package com.baeldung.instanceofalternative.mapmethod;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.instanceofalternative.model.*;

public class MapAlternative {
    private Map<Class<? extends Dinosaur>, Movement> movementHandling = new HashMap<Class<? extends Dinosaur>, Movement>();

    public MapAlternative() {
        movementHandling.put(Anatotitan.class, new Movement() {
            public String move(Dinosaur dino) {
                return "running";
            }
        });
        movementHandling.put(Euraptor.class, new Movement() {
            public String move(Dinosaur dino) {
                return "flying";
            }
        });
    }

    public String handleMessage(Dinosaur dino) {
        Movement handler = movementHandling.get(dino.getClass());

        return handler.move(dino);

    }

}
