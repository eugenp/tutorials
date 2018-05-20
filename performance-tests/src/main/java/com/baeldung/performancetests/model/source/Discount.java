package com.baeldung.performancetests.model.source;

import com.googlecode.jmapper.annotations.JGlobalMap;

import java.math.BigDecimal;
import java.util.Date;

public class Discount {
    private Date startTime;
    private Date endTime;
    private BigDecimal discountPrice;

    public Discount() {
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Discount(Date startTime, Date endTime, BigDecimal discountPrice) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPrice = discountPrice;
    }
}
