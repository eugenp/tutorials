package com.baeldung.hexagonal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.entities.TimeSheet;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

}
