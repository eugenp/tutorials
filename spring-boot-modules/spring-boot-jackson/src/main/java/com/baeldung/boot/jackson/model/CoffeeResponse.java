package com.baeldung.boot.jackson.model;

import java.time.LocalDateTime;

public class CoffeeResponse<T> {

    private LocalDateTime date;

    private T body;

    public LocalDateTime getDate() {
        return date;
    }

    public CoffeeResponse<T> setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public T getBody() {
        return body;
    }

    public CoffeeResponse<T> setBody(T body) {
        this.body = body;
        return this;
    }
}
