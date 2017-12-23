package com.baeldung.beaninjection.model;

public class Order {
    private String orderNumber;
    private String productName;
    private int quantity;
    private double price;
    private Customer customer;
    private Offers additionalOffers;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Offers getAdditionalOffers() {
        return additionalOffers;
    }

    public void setAdditionalOffers(Offers additionalOffers) {
        this.additionalOffers = additionalOffers;
    }

}
