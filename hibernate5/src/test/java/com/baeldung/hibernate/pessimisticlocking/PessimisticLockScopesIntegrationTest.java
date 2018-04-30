package com.baeldung.hibernate.pessimisticlocking;

import com.baeldung.hibernate.HibernateUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockScope;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PessimisticLockScopesIntegrationTest {

    @Test
    public void givenEclipseEntityWithJoinInheritance_whenNormalLock_thenShouldChildAndParentEntity() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        PessimisticLockingEmployee employee = new PessimisticLockingEmployee(1L, "A", "B", new BigDecimal(4.5));
        em.persist(employee);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        em = getEntityManagerWithOpenTransaction();
        PessimisticLockingEmployee foundEmployee = em.find(PessimisticLockingEmployee.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        em = getEntityManagerWithOpenTransaction();
        foundEmployee = em.find(PessimisticLockingEmployee.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
    }

    @Test
    public void givenEclipseEntityWithElementCollection_whenNormalLock_thenShouldLockOnlyOwningEntity() throws IOException {
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
    public void givenEclipseEntityWithOneToMany_whenNormalLock_thenShouldLockOnlyOwningEntity() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        PessimisticLockingStudent student = new PessimisticLockingStudent(1L, "JOE");
        PessimisticLockingCourse course = new PessimisticLockingCourse(1L, "COURSE", student);
        student.setCourses(Arrays.asList(course));
        em.persist(course);
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        em = getEntityManagerWithOpenTransaction();
        PessimisticLockingCourse foundCourse = em.find(PessimisticLockingCourse.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);

        em = getEntityManagerWithOpenTransaction();
        foundCourse = em.find(PessimisticLockingCourse.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
    }

    protected EntityManager getEntityManagerWithOpenTransaction() throws IOException {
        String propertyFileName = "hibernate-pessimistic-locking.properties";
        EntityManager entityManager = HibernateUtil.getSessionFactory(propertyFileName)
            .openSession();
        entityManager.getTransaction()
            .begin();

        return entityManager;
    }

}
