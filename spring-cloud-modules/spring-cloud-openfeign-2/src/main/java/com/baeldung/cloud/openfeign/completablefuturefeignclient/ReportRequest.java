package com.baeldung.cloud.openfeign.completablefuturefeignclient;

public class ReportRequest {

    private String content;
    private long orderId;
    private String siteId;

    public ReportRequest(String content, long orderId, String siteId) {
        this.content = content;
        this.orderId = orderId;
        this.siteId = siteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getSiteId() {
        return siteId;
    }
}
