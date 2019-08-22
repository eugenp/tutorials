package com.baeldung.hexagonal.core;

import java.util.Optional;

public interface CouponRepository {

    Optional<Coupon> findCouponByCode(String code);

}
