package com.baeldung.dddmodules.shippingcontext.model;

import java.util.List;

public class Parcel {
    private int orderId;
    private String address;
    private String trackingId;
    private List<PackageItem> packageItems;

    public Parcel(int orderId, String address, List<PackageItem> packageItems) {
        this.orderId = orderId;
        this.address = address;
        this.packageItems = packageItems;
    }

    public float calculateTotalWeight() {
        return packageItems.stream().map(PackageItem::getWeight)
          .reduce(0F, Float::sum);
    }

    public boolean isTaxable() {
        return calculateEstimatedValue() > 100;
    }

    public float calculateEstimatedValue() {
        return packageItems.stream().map(PackageItem::getWeight)
          .reduce(0F, Float::sum);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
