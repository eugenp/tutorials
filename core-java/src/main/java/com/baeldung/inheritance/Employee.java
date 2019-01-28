package com.baeldung.inheritance;

/**
 * 雇员
 * @author zn.wang
 */
public class Employee {
    private String name;
    private Car car;
    
    public Employee(String name, Car car) {
        this.name = name;
        this.car = car;
    }
    
    public Car getCar() {
    	return car;
    }
}