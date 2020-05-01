package com.baeldung.dao;

import com.baeldung.entity.Person;
import com.baeldung.entity.QPerson;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person save(final Person person) {
        em.persist(person);
        return person;
    }

    @Override
    public List<Person> findPersonsByFirstnameQueryDSL(final String firstname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).fetch();
    }

    @Override
    public List<Person> findPersonsByFirstnameAndSurnameQueryDSL(final String firstname, final String surname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname).and(person.surname.eq(surname))).fetch();
    }

    @Override
    public List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(final String firstname) {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).orderBy(person.surname.desc()).fetch();
    }

    @Override
    public int findMaxAge() {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).select(person.age.max()).fetchFirst();
    }

    @Override
    public Map<String, Integer> findMaxAgeByName() {
        final JPAQuery<Person> query = new JPAQuery<>(em);
        final QPerson person = QPerson.person;

        return query.from(person).transform(GroupBy.groupBy(person.firstname).as(GroupBy.max(person.age)));
    }

}