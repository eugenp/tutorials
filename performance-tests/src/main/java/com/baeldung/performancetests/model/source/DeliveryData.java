package com.baeldung.performancetests.model.source;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMapAccessor;

public class DeliveryData {
    private Address deliveryAddress;
    @JMapAccessor(get = "isPrePaid", set = "setPrePaid")
    private boolean isPrePaid;
    private String trackingCode;
    private int expectedDeliveryTimeInDays;

    public DeliveryData() {
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public boolean isPrePaid() {
        return isPrePaid;
    }

    public void setPrePaid(boolean prePaid) {
        isPrePaid = prePaid;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public int getExpectedDeliveryTimeInDays() {
        return expectedDeliveryTimeInDays;
    }

    public void setExpectedDeliveryTimeInDays(int expectedDeliveryTimeInDays) {
        this.expectedDeliveryTimeInDays = expectedDeliveryTimeInDays;
    }

    public DeliveryData(Address deliveryAddress, boolean isPrePaid, String trackingCode, int expectedDeliveryTimeInDays) {
        this.deliveryAddress = deliveryAddress;
        this.isPrePaid = isPrePaid;
        this.trackingCode = trackingCode;
        this.expectedDeliveryTimeInDays = expectedDeliveryTimeInDays;
    }
}
