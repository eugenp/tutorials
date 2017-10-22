package com.baeldung.spring.beans.scopes.domain;

import java.util.Date;

public class Coffee {

    private String type;

    private Date brewedTime = new Date();

    public Coffee() {}

    public Coffee(String type) {
        this(type, new Date());
    }

    public Coffee(String type, Date brewedTime) {
        this.type = type;
        this.brewedTime = brewedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBrewedTime() {
        return brewedTime;
    }

    public void setBrewedTime(Date brewedTime) {
        this.brewedTime = brewedTime;
    }

    public String toString() {
        return type + " Coffee brewed at " + brewedTime;
    }
}
