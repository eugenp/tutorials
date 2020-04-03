package com.baeldung.hexagonarch.adapters.driven;

import org.hexagonarch.ports.driven.Person;
import org.hexagonarch.ports.driven.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PersonDatabaseRepository implements PersonRepository {

    private List<Person> persons = Arrays.asList(new Person() {
        @Override
        public String name() {
            return "Johnny";
        }

        @Override
        public String streetName() {
            return "Abbey Road";
        }

        @Override
        public String city() {
            return "London";
        }

        @Override
        public String zipCode() {
            return "NX8";
        }
    });

    private SpringJpaPersonRepository jpaPersonRepository = new SpringJpaPersonRepository() {
        @Override
        public Optional<Collection<Person>> findByStreetNameAndCityAndZipCode(String streetName, String city, String zipCode) {
            return Optional.of(persons);
        }

        @Override
        public List<Person> findAll() {
            return persons;
        }

        @Override
        public List<Person> findAll(Sort sort) {
            return persons;
        }

        @Override
        public List<Person> findAll(Iterable<Integer> integers) {
            return null;
        }

        @Override
        public <S extends Person> List<S> save(Iterable<S> entities) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Person> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public void deleteInBatch(Iterable<Person> entities) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Person getOne(Integer integer) {
            return null;
        }

        @Override
        public Page<Person> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Person> S save(S entity) {
            return null;
        }

        @Override
        public Person findOne(Integer integer) {
            return null;
        }

        @Override
        public boolean exists(Integer integer) {
            return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void delete(Integer integer) {

        }

        @Override
        public void delete(Person entity) {

        }

        @Override
        public void delete(Iterable<? extends Person> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };

    public Optional<Collection<Person>> findPersonsInNeighbourhood(String streetName, String city, String zipCode) {
        return jpaPersonRepository.findByStreetNameAndCityAndZipCode(streetName, city, zipCode);
    }
}
