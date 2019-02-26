package com.baeldung.boot.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.boot.domain.Possession;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
