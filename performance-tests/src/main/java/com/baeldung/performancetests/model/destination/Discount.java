package com.baeldung.performancetests.model.destination;

import com.google.common.base.Objects;
import com.googlecode.jmapper.annotations.JGlobalMap;

import java.math.BigDecimal;
import java.time.LocalDate;

@JGlobalMap
public class Discount {
    private LocalDate startTime;
    private LocalDate endTime;
    private BigDecimal discountPrice;

    public Discount() {
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == com.baeldung.performancetests.model.source.Discount.class) {
            com.baeldung.performancetests.model.source.Discount discount =
              (com.baeldung.performancetests.model.source.Discount) o;
            return Objects.equal(startTime, discount.getStartTime()) &&
              Objects.equal(endTime, discount.getEndTime()) &&
              Objects.equal(discountPrice, discount.getDiscountPrice());
        }
        if(o.getClass() != getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equal(startTime, discount.startTime) &&
                Objects.equal(endTime, discount.endTime) &&
                Objects.equal(discountPrice, discount.discountPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startTime, endTime, discountPrice);
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
