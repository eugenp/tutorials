package com.baeldung.cloud.openfeign.completablefuturefeignclient;

public class Purchase {

    String siteId;

    public Purchase(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }
}
