package com.baeldung.cloud.openfeign.completablefuturefeignclient;

public class Purchase {

    private String siteId;
    private long orderId;

    public Purchase(String siteId, long orderId) {
        this.siteId = siteId;
        this.orderId = orderId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
