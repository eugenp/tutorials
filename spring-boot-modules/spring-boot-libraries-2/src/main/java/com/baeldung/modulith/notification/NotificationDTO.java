package com.baeldung.modulith.notification;

import java.util.Date;

public class NotificationDTO {
    private Date date;
    private String format;
    private String productName;

    public NotificationDTO(Date date, String format, String productName) {
        this.date = date;
        this.format = format;
        this.productName = productName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
