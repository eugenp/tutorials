package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaMaker {

    private final PizzaBaker pizzaBaker;

    private Pineappler pineappler;

    private Baconizer baconizer;

    @Autowired
    public PizzaMaker(PizzaBaker pizzaBaker) {
        this.pizzaBaker = pizzaBaker;
    }

    @Autowired
    public void setPineappler(Pineappler pineappler) {
        this.pineappler = pineappler;
    }

    @Autowired
    public void setPizzaBaconizer(Baconizer baconizer) {
        this.baconizer = baconizer;
    }
}
