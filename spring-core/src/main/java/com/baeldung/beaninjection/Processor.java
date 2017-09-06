package com.baeldung.beaninjection;

class Processor {

    private String name;

    Processor(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
