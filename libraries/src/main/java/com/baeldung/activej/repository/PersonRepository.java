package com.baeldung.activej.repository;

import java.time.Duration;

import javax.sql.DataSource;

import com.baeldung.activej.model.Person;

import io.activej.promise.Promise;
import io.activej.promise.Promises;

public class PersonRepository {
    private final DataSource dataSource;

    public PersonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Promise<Person> findPerson(String name) {
        return Promises
          .delay(Duration.ofMillis(100), new Person(name, name + " description"));
    }
}
