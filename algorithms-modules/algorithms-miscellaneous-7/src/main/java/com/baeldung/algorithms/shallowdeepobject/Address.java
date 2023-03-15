package com.baeldung.algorithms.shallowdeepobject;

public class Address implements Cloneable{

    public String houseNumber;
    public String streetName;
    public String city;

    public Address(){

    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return (Address) super.clone();
    }

    @Override
    public String toString(){
        return "Address: " + houseNumber + ", " + streetName + ", " + city;
    }
}