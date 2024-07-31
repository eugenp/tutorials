package com.baeldung.ratelimiting.dto;

public class AreaV1 {

    private String shape;
    private Double area;
    
    public AreaV1(String shape, Double area) {
        this.area = area;
        this.shape = shape;
    }
    
    public Double getArea() {
        return area;
    }
    
    public String getShape() {
        return shape;
    }
}
