package org.baeldung.dao;

import java.util.List;
import java.util.Map;

import org.baeldung.entity.Person;

public interface PersonDao {

    public Person save(Person person);

    public List<Person> findPersonsByFirstnameQueryDSL(String firstname);

    public List<Person> findPersonsByFirstnameAndSurnameQueryDSL(String firstname, String surname);

    public List<Person> findPersonsByFirstnameInDescendingOrderQueryDSL(String firstname);

    public int findMaxAge();

    public Map<String, Integer> findMaxAgeByName();

}