package com.baeldung.hexagonal.springapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataProperties;

import com.baeldung.hexagonal.core.Coupon;
import com.baeldung.hexagonal.springapp.entity.CouponEntity;

@Mapper
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    CouponEntity toEntity(Coupon model);

    default Coupon toModel(CouponEntity entity) {
        return new Coupon(entity.getCode(), entity.getFixedDiscount(), entity.getDiscountPercentage());
    }

}
