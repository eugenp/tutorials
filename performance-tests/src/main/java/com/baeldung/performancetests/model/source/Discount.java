package com.baeldung.performancetests.model.source;

import com.googlecode.jmapper.annotations.JGlobalMap;

import java.math.BigDecimal;
public class Discount {
    private String startTime;
    private String endTime;
    private BigDecimal discountPrice;

    public Discount() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Discount(String startTime, String endTime, BigDecimal discountPrice) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPrice = discountPrice;
    }
}
