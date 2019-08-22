package com.baeldung.hexagonal.core;

public class Coupon {

    private final String code;

    private final Integer fixedDiscount;

    private final Integer discountPercentage;

    public Coupon(String code, Integer fixedDiscount, Integer discountPercentage) {
        if ((fixedDiscount == null) == (discountPercentage == null)) {
            throw new IllegalArgumentException("exactly one of fixedDiscount and discountPercentage should be null");
        }
        this.code = code;
        this.fixedDiscount = fixedDiscount;
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return code;
    }

    public Integer getFixedDiscount() {
        return fixedDiscount;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isFixed() {
        return fixedDiscount != null;
    }

}
