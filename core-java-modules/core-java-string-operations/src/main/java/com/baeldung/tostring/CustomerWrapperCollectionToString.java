package com.baeldung.tostring;

import java.util.List;

public class CustomerWrapperCollectionToString extends Customer {
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
        return "Customer [score=" + score + ", orders=" + orders + ", fullname=" + fullname + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + "]";
    }
}
