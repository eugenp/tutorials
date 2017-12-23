package com.baeldung.di.types.xml;

public class Secretary {

    private int floor;
    private Employee employee;

    public Secretary(int floor, Employee employee) {
        this.floor = floor;
        this.employee = employee;
    }

    public Secretary() {
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Secretary{" +
                "floor=" + floor +
                ", employee=" + employee +
                '}';
    }
}
