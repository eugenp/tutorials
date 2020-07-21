package com.baeldung.resttemplate.custom.dto;


public class BaeldungItemV2 {
    private final String itemName;

    public BaeldungItemV2(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
