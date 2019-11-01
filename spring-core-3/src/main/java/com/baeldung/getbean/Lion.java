package com.baeldung.getbean;

class Lion implements AnnotationConfig.Animal {
    private String name;

    Lion(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
