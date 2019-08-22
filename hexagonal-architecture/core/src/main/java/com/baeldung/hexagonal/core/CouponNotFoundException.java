package com.baeldung.hexagonal.core;

public class CouponNotFoundException extends RuntimeException {

    private String code;

    public CouponNotFoundException(String couponCode) {
        super("coupon not found by code " + couponCode);
    }

    public String getCode() {
        return code;
    }
}
