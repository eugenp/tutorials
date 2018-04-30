package com.baeldung.eclipselink.springdata.pessimisticlocking;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PessimisticLockScopesIntegrationTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    public void givenEclipseEntityWithJoinInheritance_whenNormalLock_thenShouldChildAndParentEntity() {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Employee employee = new Employee(1L, "A", "B", new BigDecimal(4.5));
        em.persist(employee);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        em = getEntityManagerWithOpenTransaction();
        Employee foundEmployee = em.find(Employee.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        em = getEntityManagerWithOpenTransaction();
        foundEmployee = em.find(Employee.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
    }

    @Test
    public void givenEclipseEntityWithElementCollection_whenNormalLock_thenShouldLockOnlyOwningEntity() {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Address address = new Address("Poland", "Warsaw");
        Customer customer = new Customer(1L, "A", "B", Arrays.asList(address));
        em.persist(customer);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        em = getEntityManagerWithOpenTransaction();
        Customer foundCustomer = em.find(Customer.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        em = getEntityManagerWithOpenTransaction();
        foundCustomer = em.find(Customer.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
    }

    @Test
    public void givenEclipseEntityWithOneToMany_whenNormalLock_thenShouldLockOnlyOwningEntity() {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Student student = new Student(1L, "JOE");
        Course course = new Course(1L, "COURSE", student);
        student.setCourses(Arrays.asList(course));
        em.persist(course);
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        em = getEntityManagerWithOpenTransaction();
        Course foundCourse = em.find(Course.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        em = getEntityManagerWithOpenTransaction();
        foundCourse = em.find(Course.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
    }

    protected EntityManager getEntityManagerWithOpenTransaction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction()
            .begin();
        return entityManager;
    }
}
