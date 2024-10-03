package com.baeldung.spring.data.jpa.filtering;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WearableBrokenRepository extends JpaRepository<WearableInvalidEntity, Long> {
    List<WearableInvalidEntity> findAllByOrderByPriceAscSensorTypeAscPopularityIndexDesc();

}

