package com.baeldung.performancetests.model.destination;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMapAccessor;

import java.util.Objects;

@JGlobalMap
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(o.getClass() == com.baeldung.performancetests.model.source.DeliveryData.class) {
            com.baeldung.performancetests.model.source.DeliveryData deliveryData =
              (com.baeldung.performancetests.model.source.DeliveryData) o;
            return isPrePaid == deliveryData.isPrePaid() &&
              expectedDeliveryTimeInDays == deliveryData.getExpectedDeliveryTimeInDays() &&
              Objects.equals(deliveryAddress, deliveryData.getDeliveryAddress()) &&
              Objects.equals(trackingCode, deliveryData.getTrackingCode());
        }
        if (o.getClass() != getClass()) return false;
        DeliveryData that = (DeliveryData) o;
        return isPrePaid == that.isPrePaid &&
          expectedDeliveryTimeInDays == that.expectedDeliveryTimeInDays &&
          Objects.equals(deliveryAddress, that.deliveryAddress) &&
          Objects.equals(trackingCode, that.trackingCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deliveryAddress, isPrePaid, trackingCode, expectedDeliveryTimeInDays);
    }
}
