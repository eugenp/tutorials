package com.baeldung.hexagonal.model;

public class SportRevenue {
    private String name;
    private double revenue;
    
    public SportRevenue(String name, double revenue) {
        this.name = name;
        this.revenue = revenue;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getRevenue() {
        return revenue;
    }
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    @Override
    public String toString() {
        return "SportRevenue [name=" + name + ", revenue=" + revenue + "]";
    }
    
}
