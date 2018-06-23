package com.baeldung.reactive.events;

import java.util.Date;

public class SmsEvent {

    private String mobile;
    private String message;
    private String shortCode;
    private Date dispatchDate;

    public SmsEvent() {
        super();
    }

    public SmsEvent(String mobile, String message, String shortCode, Date dispatchDate) {
        this.mobile = mobile;
        this.message = message;
        this.shortCode = shortCode;
        this.dispatchDate = dispatchDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    @Override
    public String toString() {
        return "SmsEvent{" +
                "mobile='" + mobile + '\'' +
                ", message='" + message + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", dispatchDate=" + dispatchDate +
                '}';
    }
}
