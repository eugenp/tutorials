package com.baeldung.spring.data.neo4j.repostory;

import com.baeldung.spring.data.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GraphRepository<Person> {
}
