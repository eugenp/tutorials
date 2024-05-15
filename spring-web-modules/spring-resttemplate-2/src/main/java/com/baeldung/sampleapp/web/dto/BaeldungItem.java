package com.baeldung.sampleapp.web.dto;

public class BaeldungItem {
    private final String itemId;

    public BaeldungItem(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
