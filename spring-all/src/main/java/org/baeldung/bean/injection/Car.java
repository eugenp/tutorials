package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private Brake brake;

    @Autowired
    public Car(Brake brake) {
        this.brake = brake;
    }

    public Brake getBrake() {
        return brake;
    }
}
