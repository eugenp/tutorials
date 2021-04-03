package com.baeldung.hexagonal.infrastructure.database;

import com.baeldung.hexagonal.core.components.domain.VaccinationDetails;
import org.springframework.data.repository.CrudRepository;

public interface VaccinationRepository extends CrudRepository<VaccinationDetails,Integer> {
}
