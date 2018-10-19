package org.baeldung.customannotation;

import org.springframework.stereotype.Repository;

@Repository
public class BeanWithGenericDAO {

    @DataAccess(entity = Person.class)
    private GenericDAO<Person> personGenericDAO;

    public BeanWithGenericDAO() {
    }

    public GenericDAO<Person> getPersonGenericDAO() {
        return personGenericDAO;
    }

}
