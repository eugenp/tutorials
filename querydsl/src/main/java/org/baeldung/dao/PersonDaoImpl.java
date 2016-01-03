package org.baeldung.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import org.baeldung.entity.Person;
import org.baeldung.entity.QPerson;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    public Person save(Person person) {
        em.persist(person);
        return person;
    }

    public List<Person> findPersonsByFirstnameQueryDSL(String firstname) {
        JPAQuery query = new JPAQuery(em);
        QPerson person = QPerson.person;

        return query.from(person).where(person.firstname.eq(firstname)).list(person);
    }

}