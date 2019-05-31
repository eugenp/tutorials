package org.java.spring.hexagonalarchitecture.inside;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositoryPort extends JpaRepository<Person, Long> {

}
