package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class ShipWithFinalDependency {

    private final Helm helm;

    @Autowired
    public ShipWithFinalDependency(Helm helm) {
        this.helm = helm;
    }

    public Helm getHelm() {
        return helm;
    }

}
