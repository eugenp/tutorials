package com.baeldung.beaninjection.domain;

public class Shield {
    private int defencePoints;

    public Shield(int defencePoints) {
        this.defencePoints = defencePoints;
    }

    @Override
    public String toString() {
        return "Shield{" +
                "defencePoints=" + defencePoints +
                '}';
    }
}
