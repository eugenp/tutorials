package org.baeldung.hex.ports.outbound;

import org.baeldung.hex.domain.entity.CarEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CarRepository extends PagingAndSortingRepository<CarEntity, Long> {
    Optional<CarEntity> findByVinNumber(String vinNumber);
}
