package com.baeldung.hamcrest.objectmatchers;

public class City extends Location {
    String name;
    String state;

    public City(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        if (this.name == null && this.state == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("Name: ");
        sb.append(this.name);
        sb.append(", ");
        sb.append("State: ");
        sb.append(this.state);
        sb.append("]");
        return sb.toString();
    }
}
