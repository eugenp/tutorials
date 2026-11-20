package com.baeldung.causeway.assets.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAllByOrderBySerialNumberAsc();

    List<Asset> findBySerialNumberContainingIgnoreCaseOrderBySerialNumberAsc(String serialNumber);

    Optional<Asset> findBySerialNumberIgnoreCase(String serialNumber);
}
