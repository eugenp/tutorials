package com.baeldung.courier.delivery.service.core.domain;

import com.baeldung.courier.delivery.service.constants.Constants.CourierStatus;

public class Courier {
    private String item;
    private String from;
    private String to;
    private int trackingId;
    private CourierStatus courierStatus;

    public Courier(String item, String from, String to) {
        this.item = item;
        this.from = from;
        this.to = to;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public CourierStatus getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(CourierStatus courierStatus) {
        this.courierStatus = courierStatus;
    }
}
