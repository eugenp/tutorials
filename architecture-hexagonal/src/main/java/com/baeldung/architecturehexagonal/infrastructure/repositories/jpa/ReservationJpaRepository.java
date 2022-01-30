package com.baeldung.architecturehexagonal.infrastructure.repositories.jpa;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.ReservationDAO;
import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.TableDAO;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationDAO, Long> {
    Optional<ReservationDAO> findByCustomerName(String customerName);
    Set<ReservationDAO> findDistinctByTableIsIn(Set<TableDAO> tables);
}
