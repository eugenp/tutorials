package com.baeldung.hexagonal.movie.domain;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class Price {

    public static final Price INFINITY = Price.of(1000000.0);
    
    private final BigDecimal price;

    public static Price of(double value){
        return new Price(BigDecimal.valueOf(value));
    }

    public boolean isLessOrEqual(Price other){
        return this.price.compareTo(other.price) <= 0;
    }

    public boolean isGreaterOrEqual(Price other){
        return this.price.compareTo(other.price) >= 0;
    }

    public boolean between(Price low, Price high){
        return low.isLessOrEqual(this) && this.isLessOrEqual(high);
    }
}
