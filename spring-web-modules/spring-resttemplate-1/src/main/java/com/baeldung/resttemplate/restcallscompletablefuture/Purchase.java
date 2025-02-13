package com.baeldung.resttemplate.restcallscompletablefuture;

public class Purchase {

    private String orderDescription;
    private String paymentDescription;
    private String buyerName;
    private final String orderId;
    private final String paymentId;
    private final String userId;

    public Purchase(String orderId, String paymentId, String userId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.userId = userId;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public String getBuyerName() {
        return buyerName;
    }
}
