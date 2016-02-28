package org.baeldung.dao;

import com.mysema.query.group.GroupBy;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAQueryFactory;
import org.baeldung.entity.Person;
import org.baeldung.entity.QPerson;
import org.springframework.stereotype.Repository;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    public Person save(Person person) {
        em.persist(person);
        return person;
    }

    @Override
    public List<Person> findPersonsByFirstnameQueryDSL(String firstname) {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).list(person);
    }

    @Override
    public List<Person> findPersonsByFirstnameAndSurnameQueryDSL(String firstname, String surname) {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname).and(
                person.surname.eq(surname))).list(person);
    }

    @Override
    public List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(String firstname) {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).orderBy(
                person.surname.desc()).list(person);
    }

    @Override
    public int findMaxAge() {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).list(person.age.max()).get(0);
    }

    @Override
    public Map<String, Integer> findMaxAgeByName() {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).transform(GroupBy.groupBy(person.firstname).as(GroupBy.max(person.age)));
    }

}