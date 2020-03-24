package com.baeldung.performancetests.model.source;


import java.util.List;
public class SourceOrder {
    private String orderFinishDate;
    private PaymentType paymentType;
    private Discount discount;
    private DeliveryData deliveryData;
    private User orderingUser;
    private List<Product> orderedProducts;
    private Shop offeringShop;
    private int orderId;
    private OrderStatus status;
    private String orderDate;
    public SourceOrder() {
    }

    public User getOrderingUser() {
        return orderingUser;
    }

    public void setOrderingUser(User orderingUser) {
        this.orderingUser = orderingUser;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderFinishDate() {
        return orderFinishDate;
    }

    public void setOrderFinishDate(String orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }

    public Shop getOfferingShop() {
        return offeringShop;
    }

    public void setOfferingShop(Shop offeringShop) {
        this.offeringShop = offeringShop;
    }





    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public SourceOrder(OrderStatus status, String orderDate, String orderFinishDate, PaymentType paymentType, Discount discount, DeliveryData deliveryData, User orderingUser, List<Product> orderedProducts, Shop offeringShop, int orderId) {

        this.status = status;
        this.orderDate = orderDate;
        this.orderFinishDate = orderFinishDate;
        this.paymentType = paymentType;
        this.discount = discount;
        this.deliveryData = deliveryData;
        this.orderingUser = orderingUser;
        this.orderedProducts = orderedProducts;
        this.offeringShop = offeringShop;
        this.orderId = orderId;
    }


}
