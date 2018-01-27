package com.baeldung.firstarticle.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class RegularCar {

    @Autowired
    private Spoiler spoiler;

    public Spoiler getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Spoiler spoiler) {
        this.spoiler = spoiler;
    }

    // business logic

}
