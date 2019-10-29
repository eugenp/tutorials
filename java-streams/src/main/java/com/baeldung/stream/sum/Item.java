package com.baeldung.stream.sum;

public class Item {

    private int id;
    private Integer price;

    public Item(int id, Integer price) {
        super();
        this.id = id;
        this.price = price;
    }

    // Standard getters and setters
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
