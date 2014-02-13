package org.baeldung.jackson.sandbox;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TestElement {

    int x;

    @JsonIgnore
    private String y;

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(final String y) {
        this.y = y;
    }

}