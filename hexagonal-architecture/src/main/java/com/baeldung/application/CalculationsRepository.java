package com.baeldung.application;

import com.baeldung.domain.Calculations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationsRepository extends JpaRepository<Calculations, Long> {
}
