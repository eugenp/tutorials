package org.baeldung.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.baeldung.entity.Person;
import org.baeldung.entity.QPerson;
import org.springframework.stereotype.Repository;

import com.mysema.query.group.GroupBy;
import com.mysema.query.jpa.impl.JPAQuery;

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
        final JPAQuery query = new JPAQuery(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).list(person);
    }

    @Override
    public List<Person> findPersonsByFirstnameAndSurnameQueryDSL(final String firstname, final String surname) {
        final JPAQuery query = new JPAQuery(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname).and(person.surname.eq(surname))).list(person);
    }

    @Override
    public List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(final String firstname) {
        final JPAQuery query = new JPAQuery(em);
        final QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).orderBy(person.surname.desc()).list(person);
    }

    @Override
    public int findMaxAge() {
        final JPAQuery query = new JPAQuery(em);
        final QPerson person = QPerson.person;

        return query.from(person).list(person.age.max()).get(0);
    }

    @Override
    public Map<String, Integer> findMaxAgeByName() {
        final JPAQuery query = new JPAQuery(em);
        final QPerson person = QPerson.person;

        return query.from(person).transform(GroupBy.groupBy(person.firstname).as(GroupBy.max(person.age)));
    }

}