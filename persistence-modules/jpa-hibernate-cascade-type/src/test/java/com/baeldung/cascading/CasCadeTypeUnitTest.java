package com.baeldung.cascading;

import com.baeldung.cascading.domain.Address;
import com.baeldung.cascading.domain.Person;
import org.assertj.core.api.Assertions;
import org.hibernate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class CasCadeTypeUnitTest {
    private static SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void testPersist() {
        Person person = new Person();
        Address address = new Address();
        address.setPerson(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        session.clear();
    }

    @Test
    public void testMerge() {
        int addressId;
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        addressId = address.getId();
        session.clear();

        Address savedAddressEntity = session.find(Address.class, addressId);
        Person savedPersonEntity = savedAddressEntity.getPerson();
        savedPersonEntity.setName("devender kumar");
        savedAddressEntity.setHouseNumber(24);
        session.merge(savedPersonEntity);
        session.flush();
    }

    @Test
    public void testRemove() {
        int personId;
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        personId = person.getId();
        session.clear();

        Person savedPersonEntity = session.find(Person.class, personId);
        session.remove(savedPersonEntity);
        session.flush();
    }

    @Test
    public void testDetach() {
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        Assertions.assertThat(session.contains(person)).isTrue();
        Assertions.assertThat(session.contains(address)).isTrue();

        session.detach(person);
        Assertions.assertThat(session.contains(person)).isFalse();
        Assertions.assertThat(session.contains(address)).isFalse();
    }

    @Test
    public void testLock() {
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        Assertions.assertThat(session.contains(person)).isTrue();
        Assertions.assertThat(session.contains(address)).isTrue();

        session.detach(person);
        Assertions.assertThat(session.contains(person)).isFalse();
        Assertions.assertThat(session.contains(address)).isFalse();
        session.unwrap(Session.class)
                .buildLockRequest(new LockOptions(LockMode.NONE))
                .lock(person);

        Assertions.assertThat(session.contains(person)).isTrue();
        Assertions.assertThat(session.contains(address)).isTrue();
    }

    @Test
    public void testRefresh() {
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.persist(person);
        session.flush();
        person.setName("Devender Kumar");
        address.setHouseNumber(24);
        session.refresh(person);
        Assertions.assertThat(person.getName()).isEqualTo("devender");
        Assertions.assertThat(address.getHouseNumber()).isEqualTo(23);
    }

    @Test
    public void testReplicate() {
        Person person = buildPerson("devender");
        person.setId(2);
        Address address = buildAddress(person);
        address.setId(2);
        person.setAddresses(Arrays.asList(address));
        session.unwrap(Session.class).replicate(person, ReplicationMode.OVERWRITE);
        session.flush();
        Assertions.assertThat(person.getId()).isEqualTo(2);
        Assertions.assertThat(address.getId()).isEqualTo(2);
    }

    @Test
    public void testSaveOrUpdate() {
        Person person = buildPerson("devender");
        Address address = buildAddress(person);
        person.setAddresses(Arrays.asList(address));
        session.saveOrUpdate(person);
        session.flush();
    }

    private Address buildAddress(Person person) {
        Address address = new Address();
        address.setCity("Berlin");
        address.setHouseNumber(23);
        address.setStreet("Zeughofstraße");
        address.setZipCode(123001);
        address.setPerson(person);
        return address;
    }

    private Person buildPerson(String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }
}
