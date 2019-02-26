package com.baeldung.multipledb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.boot.domain.Possession;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
