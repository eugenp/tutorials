package com.baeldung.hibernate.union.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.union.model.Researcher;

@Repository
public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
}
