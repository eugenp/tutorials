package com.baeldung.restcallscompletablefuture;

public class Purchase {

    private String orderDescription;

    private String paymentDescription;

    private String buyerName;
    private String orderId;
    private String paymentId;
    private String userId;

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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
