package com.baeldung.hexagonal.springapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupon")
public class CouponEntity {

    @Id
    private String code;

    @Column
    private Integer fixedDiscount;

    @Column
    private Integer discountPercentage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getFixedDiscount() {
        return fixedDiscount;
    }

    public void setFixedDiscount(Integer fixedDiscount) {
        this.fixedDiscount = fixedDiscount;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
