package com.baeldung.injection;

public class Restaurant {

    private String name;

    public Restaurant(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Restaurant [");
        if (name != null) {
            builder.append("name=");
            builder.append(name);
        }
        builder.append("]");
        return builder.toString();
    }

}
