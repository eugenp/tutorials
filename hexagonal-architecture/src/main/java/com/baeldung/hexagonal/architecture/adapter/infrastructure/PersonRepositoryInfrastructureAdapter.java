package com.baeldung.hexagonal.architecture.adapter.infrastructure;

import com.baeldung.hexagonal.architecture.port.infrastructure.PersonInfrastructurePort;
import com.baeldung.hexagonal.architecture.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.baeldung.hexagonal.architecture.utils.SqlQueries.getPersonQuery;
import static com.baeldung.hexagonal.architecture.utils.SqlQueries.insertPersonQuery;

@Repository
public class PersonRepositoryInfrastructureAdapter implements PersonInfrastructurePort {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void addPerson(Person person) {
        jdbcTemplate.update(insertPersonQuery, person.getId(), person.getName());
    }

    @Override
    public Person getPerson(int personId) {
        return jdbcTemplate.queryForObject(getPersonQuery, new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class));
    }
}
