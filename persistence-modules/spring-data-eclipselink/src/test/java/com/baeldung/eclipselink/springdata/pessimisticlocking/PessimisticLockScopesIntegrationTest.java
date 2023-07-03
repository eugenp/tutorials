package com.baeldung.eclipselink.springdata.pessimisticlocking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.persistence.*;
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
    public void givenEntityWithJoinInheritance_whenLock_thenNormalAndExtendScopesLockParentAndChildEntity() {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Employee employee = new Employee(1L, "JOE", "DOE", new BigDecimal(4.5));
        em.persist(employee);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        EntityManager em2 = getEntityManagerWithOpenTransaction();
        Employee foundEmployee = em2.find(Employee.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em2.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundEmployee = em3.find(Employee.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em3.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    @Test
    public void givenEntityWithElementCollection_whenLock_thenExtendScopeLocksAlsoCollectionTable() {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Address address = new Address("Poland", "Warsaw");
        Customer customer = new Customer(1L, "JOHN", "SMITH", Arrays.asList(address));
        em.persist(customer);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        EntityManager em2 = getEntityManagerWithOpenTransaction();
        Customer foundCustomer = em2.find(Customer.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em2.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundCustomer = em3.find(Customer.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em3.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    @Test
    public void givenEclipseEntityWithOneToMany_whenLock_thenExtendedLockAlsoJoinTable() {
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
        EntityManager em2 = getEntityManagerWithOpenTransaction();
        Course foundCourse = em2.find(Course.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em2.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundCourse = em3.find(Course.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em3.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    protected EntityManager getEntityManagerWithOpenTransaction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction()
            .begin();
        return entityManager;
    }
}
