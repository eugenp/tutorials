package com.baeldung.beaninjection;

class Motherboard {

    private String name;

    Motherboard(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
