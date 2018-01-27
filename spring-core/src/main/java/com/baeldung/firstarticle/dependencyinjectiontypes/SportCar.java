package com.baeldung.firstarticle.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class SportCar {

    private Spoiler spoiler;

    @Autowired
    public SportCar(Spoiler spoiler) {
        this.spoiler = spoiler;
    }

    public Spoiler getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Spoiler spoiler) {
        this.spoiler = spoiler;
    }

    // business logic

}
