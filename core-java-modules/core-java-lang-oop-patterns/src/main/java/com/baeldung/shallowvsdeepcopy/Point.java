package com.baeldung.shallowvsdeepcopy;

public class Point implements Cloneable {

    private String x;
    private String y;

    public Point(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    protected Point clone() {
        return new Point(this.getX(), this.getY());
    }

}
