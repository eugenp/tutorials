package com.baeldung.jackson.objectmapper.dto;

import java.util.Date;

public class Request {
    Car car;
    Date datePurchased;

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(final Date datePurchased) {
        this.datePurchased = datePurchased;
    }
}