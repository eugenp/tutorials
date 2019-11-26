package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class Ship {

    @Autowired
    private Helm helm;

    public Ship() {
        helm = new Helm();
    }

    public Ship(Helm helm) {
        this.helm = helm;
    }

    @Autowired
    public void setHelm(Helm helm) {
        this.helm = helm;
    }

    public Helm getHelm() {
        return this.helm;
    }
}
