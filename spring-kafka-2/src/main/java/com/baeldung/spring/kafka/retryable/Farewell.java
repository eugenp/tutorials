package com.baeldung.spring.kafka.retryable;

public class Farewell {

    private String message;
    private Integer remainingMinutes;

    public Farewell() {
    }

    public Farewell(String message, Integer remainingMinutes) {
        this.message = message;
        this.remainingMinutes = remainingMinutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRemainingMinutes() {
        return remainingMinutes;
    }

    public void setRemainingMinutes(Integer remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    @Override
    public String toString() {
        return message + ". In " + remainingMinutes + "!";
    }

}
