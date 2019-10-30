package com.baeldung.string.tostring;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CustomerReflectionToString extends Customer {

    private Integer score;
    private List<String> orders;
    private StringBuffer fullname;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public StringBuffer getFullname() {
        return fullname;
    }

    public void setFullname(StringBuffer fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
