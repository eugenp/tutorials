package com.baeldung.dddmodules.shippingcontext.model;

import java.util.List;

public class ShippableOrder {
    private int orderId;
    private String address;
    private List<PackageItem> packageItems;

    public ShippableOrder(int orderId, List<PackageItem> packageItems) {
        this.orderId = orderId;
        this.packageItems = packageItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<PackageItem> getPackageItems() {
        return packageItems;
    }

    public void setPackageItems(List<PackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
