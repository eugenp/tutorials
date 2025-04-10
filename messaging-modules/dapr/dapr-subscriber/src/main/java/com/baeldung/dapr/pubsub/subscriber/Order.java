package com.baeldung.dapr.pubsub.subscriber;

public class Order {
    private String id;
    private String item;
    private Integer amount;

    public Order() {
    }

    public Order(String id, String item, Integer amount) {
        this.id = id;
        this.item = item;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
