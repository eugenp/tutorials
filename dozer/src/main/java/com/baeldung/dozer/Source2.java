package com.baeldung.dozer;

public class Source2 {
    private String id;
    private double points;

    public Source2() {

    }

    public Source2(String id, double points) {
        super();
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Source2 [id=" + id + ", points=" + points + "]";
    }

}
