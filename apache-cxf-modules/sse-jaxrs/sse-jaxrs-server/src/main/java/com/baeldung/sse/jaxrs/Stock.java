package com.baeldung.sse.jaxrs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Stock {
    private Integer id;
    private String name;
    private BigDecimal price;
    LocalDateTime dateTime;

    public Stock(Integer id, String name, BigDecimal price, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
