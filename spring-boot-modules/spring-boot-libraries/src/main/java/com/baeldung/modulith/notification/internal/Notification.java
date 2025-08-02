package com.baeldung.modulith.notification.internal;

import java.util.Date;

public class Notification {
    private Date date;
    private NotificationType format;
    private String productName;

    public Notification() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NotificationType getFormat() {
        return format;
    }

    public void setFormat(NotificationType format) {
        this.format = format;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
