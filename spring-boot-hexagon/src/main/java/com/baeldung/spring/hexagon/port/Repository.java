package com.baeldung.spring.hexagon.port;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.hexagon.domain.SuperHero;

public interface Repository extends JpaRepository<SuperHero, Integer> {}
