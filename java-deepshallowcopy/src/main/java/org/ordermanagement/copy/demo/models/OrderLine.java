package org.ordermanagement.copy.demo.models;

public class OrderLine implements Cloneable {
    private String description;
    private int quantity;

    public OrderLine(String description, int quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public OrderLine clone() throws CloneNotSupportedException {
        return (OrderLine) super.clone();
    }

}
