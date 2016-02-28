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

import java.util.Map;


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

    @Test
    public void testMultipleFilter() {
        personDao.save(new Person("Erich", "Gamma"));
        Person person = personDao.save(new Person("Ralph", "Beck"));
        Person person2 = personDao.save(new Person("Ralph", "Johnson"));

        Person personFromDb = personDao.findPersonsByFirstnameAndSurnameQueryDSL("Ralph", "Johnson").get(0);
        Assert.assertNotSame(person.getId(), personFromDb.getId());
        Assert.assertEquals(person2.getId(), personFromDb.getId());
    }

    @Test
    public void testOrdering() {
        Person person = personDao.save(new Person("Kent", "Gamma"));
        personDao.save(new Person("Ralph", "Johnson"));
        Person person2 = personDao.save(new Person("Kent", "Zivago"));

        Person personFromDb = personDao.findPersonsByFirstnameInDescendingOrderQueryDSL("Kent").get(0);
        Assert.assertNotSame(person.getId(), personFromDb.getId());
        Assert.assertEquals(person2.getId(), personFromDb.getId());
    }

    @Test
    public void testMaxAge() {
        personDao.save(new Person("Kent", "Gamma", 20));
        personDao.save(new Person("Ralph", "Johnson", 35));
        personDao.save(new Person("Kent", "Zivago", 30));

        int maxAge = personDao.findMaxAge();
        Assert.assertTrue(maxAge == 35);
    }

    @Test
    public void testMaxAgeByName() {
        personDao.save(new Person("Kent", "Gamma", 20));
        personDao.save(new Person("Ralph", "Johnson", 35));
        personDao.save(new Person("Kent", "Zivago", 30));

        Map<String, Integer> maxAge = personDao.findMaxAgeByName();
        Assert.assertTrue(maxAge.size() == 2);
        Assert.assertSame(35, maxAge.get("Ralph"));
        Assert.assertSame(30, maxAge.get("Kent"));
    }
}