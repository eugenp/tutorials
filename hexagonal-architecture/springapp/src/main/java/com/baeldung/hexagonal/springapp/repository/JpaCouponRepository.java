package com.baeldung.hexagonal.springapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.CouponRepository;
import com.baeldung.hexagonal.springapp.entity.CouponEntity;

@Repository
public interface JpaCouponRepository extends CouponRepository, CrudRepository<CouponEntity, String> {
}
