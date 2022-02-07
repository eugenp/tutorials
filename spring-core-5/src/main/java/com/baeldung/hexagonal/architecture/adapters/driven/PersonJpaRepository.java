package com.baeldung.hexagonal.architecture.adapters.driven;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonJpaRepository extends JpaRepository<PersonJpaEntity, Long> {
}
