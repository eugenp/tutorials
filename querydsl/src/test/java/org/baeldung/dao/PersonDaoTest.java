package org.baeldung.dao;

import junit.framework.Assert;
import org.baeldung.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    @Test
    public void testCreation() {
        personDao.save(new Person("Erich", "Gamma"));
        Person person = new Person("Kent", "Beck");
        personDao.save(person);
        personDao.save(new Person("Ralph", "Johnson"));

        Person personFromDb = personDao.findPersonsByFirstnameQueryDSL("Kent").get(0);
        Assert.assertEquals(person.getId(), personFromDb.getId());
    }
}