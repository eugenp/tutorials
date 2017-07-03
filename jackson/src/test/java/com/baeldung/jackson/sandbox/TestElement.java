package com.baeldung.jackson.sandbox;

class TestElement {

    private int x;

    private transient String y;

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