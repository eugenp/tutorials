package com.baeldung.dynamicquery.repository;

import com.baeldung.dynamicquery.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
