package com.baeldung.spring.hexagonal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAthleteRepositoryService extends JpaRepository<Athlete, Long> {

}
