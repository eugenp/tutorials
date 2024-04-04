package com.baeldung.multiplerestcallscompletablefuture;

public class Purchase {

    private String orderDescription;

    private String paymentDescription;

    private String buyerName;

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
