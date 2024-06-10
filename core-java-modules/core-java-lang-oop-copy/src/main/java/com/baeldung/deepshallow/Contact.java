package com.baeldung.deepshallow;

public class Contact implements Cloneable{
    private String city;
    // other fields ....

    public Contact(String city){
        this.city = city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
