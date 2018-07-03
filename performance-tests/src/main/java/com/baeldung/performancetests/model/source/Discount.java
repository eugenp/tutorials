package com.baeldung.performancetests.model.source;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Discount {
    private LocalDate startTime;
    private LocalDate endTime;
    private BigDecimal discountPrice;

    public Discount() {
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Discount(LocalDate startTime, LocalDate endTime, BigDecimal discountPrice) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPrice = discountPrice;
    }
}
