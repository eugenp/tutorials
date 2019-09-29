package com.baeldung.restassured;

public class Odd {

    float price;
    int status;
    float ck;
    String name;

    Odd(float price, int status, float ck, String name) {
        this.price = price;
        this.status = status;
        this.ck = ck;
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getCk() {
        return ck;
    }

    public void setCk(float ck) {
        this.ck = ck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
