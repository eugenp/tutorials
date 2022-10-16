package com.baeldung.java.concept;


public class Smartphone implements Cloneable {
    private String brand;
    private String model;
    private double price;
    private CpuSpecification cpuSpecification;

    public Smartphone(String brand, String model, double price, CpuSpecification cpuSpecification) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.cpuSpecification = cpuSpecification;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CpuSpecification getCpuSpecification() {
        return this.cpuSpecification;
    }

    public void setCpuSpecification(CpuSpecification cpuSpecification) {
        this.cpuSpecification = cpuSpecification;
    }

    @Override
    public Smartphone clone() throws CloneNotSupportedException{
        Smartphone copy = (Smartphone)super.clone();
        copy.setCpuSpecification(cpuSpecification.clone());
        return copy;
    }

    public Smartphone shallowCopy() {
        return new Smartphone(brand, model, price, cpuSpecification);
    }

    public Smartphone deepCopy() {
        try {
            return this.clone();
        } catch (Exception ex) {
            return null;
        }
    }
}

