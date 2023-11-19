package com.baeldung.dao;

import java.util.Map;

import com.baeldung.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.Assert;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class PersonDaoIntegrationTest {

    @Autowired
    private PersonDao personDao;

    //

    @Test
    public void givenExistingPersons_whenFindingPersonByFirstName_thenFound() {
        personDao.save(new Person("Erich", "Gamma"));
        final Person person = new Person("Kent", "Beck");
        personDao.save(person);
        personDao.save(new Person("Ralph", "Johnson"));

        final Person personFromDb = personDao.findPersonsByFirstnameQueryDSL("Kent").get(0);
        Assert.assertEquals(person.getId(), personFromDb.getId());
    }

    @Test
    public void givenExistingPersons_whenFindingPersonByFirstNameAndSurName_thenFound() {
        personDao.save(new Person("Erich", "Gamma"));
        final Person person = personDao.save(new Person("Ralph", "Beck"));
        final Person person2 = personDao.save(new Person("Ralph", "Johnson"));

        final Person personFromDb = personDao.findPersonsByFirstnameAndSurnameQueryDSL("Ralph", "Johnson").get(0);
        Assert.assertNotSame(person.getId(), personFromDb.getId());
        Assert.assertEquals(person2.getId(), personFromDb.getId());
    }

    @Test
    public void givenExistingPersons_whenFindingPersonByFirstNameInDescendingOrder_thenFound() {
        final Person person = personDao.save(new Person("Kent", "Gamma"));
        personDao.save(new Person("Ralph", "Johnson"));
        final Person person2 = personDao.save(new Person("Kent", "Zivago"));

        final Person personFromDb = personDao.findPersonsByFirstnameInDescendingOrderQueryDSL("Kent").get(0);
        Assert.assertNotSame(person.getId(), personFromDb.getId());
        Assert.assertEquals(person2.getId(), personFromDb.getId());
    }

    @Test
    public void givenExistingPersons_whenFindingMaxAge_thenFound() {
        personDao.save(new Person("Kent", "Gamma", 20));
        personDao.save(new Person("Ralph", "Johnson", 35));
        personDao.save(new Person("Kent", "Zivago", 30));

        final int maxAge = personDao.findMaxAge();
        Assert.assertTrue(maxAge == 35);
    }

    @Test
    public void givenExistingPersons_whenFindingMaxAgeByName_thenFound() {
        personDao.save(new Person("Kent", "Gamma", 20));
        personDao.save(new Person("Ralph", "Johnson", 35));
        personDao.save(new Person("Kent", "Zivago", 30));

        final Map<String, Integer> maxAge = personDao.findMaxAgeByName();
        Assert.assertTrue(maxAge.size() == 2);
        Assert.assertSame(35, maxAge.get("Ralph"));
        Assert.assertSame(30, maxAge.get("Kent"));
    }
}