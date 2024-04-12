package com.baeldung.hibernate.pessimisticlocking;

import com.baeldung.hibernate.HibernateUtil;

import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PessimisticLockScope;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PessimisticLockScopesIntegrationTest {
    
    private static SessionFactory sessionFactory;

    @Test
    public void givenEclipseEntityWithJoinInheritance_whenNormalLock_thenShouldChildAndParentEntity() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        PessimisticLockingEmployee employee = new PessimisticLockingEmployee(1L, "JOHN", "SMITH", new BigDecimal(4.5));
        em.persist(employee);
        em.getTransaction()
            .commit();
        em.close();

        // NORMAL SCOPE
        EntityManager em2 = getEntityManagerWithOpenTransaction();
        PessimisticLockingEmployee foundEmployee = em2.find(PessimisticLockingEmployee.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em2.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("jakarta.persistence", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundEmployee = em3.find(PessimisticLockingEmployee.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em3.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    @Test
    public void givenEntityWithElementCollection_whenLock_thenHibernateExtendedScopeLockOnlyOwningEntity() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        Address address = new Address("Poland", "Warsaw");
        Customer customer = new Customer(1L, "JOE", "DOE", Arrays.asList(address));
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
        map.put("jakarta.persistence", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundCustomer = em3.find(Customer.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em2.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    @Test
    public void givenEntityWithOneToMany_whenLock_thenHibernateExtendedScopeLockOnlyOwningEntity() throws IOException {
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
        EntityManager em2 = getEntityManagerWithOpenTransaction();
        PessimisticLockingCourse foundCourse = em2.find(PessimisticLockingCourse.class, 1L, LockModeType.PESSIMISTIC_WRITE);
        em2.getTransaction()
            .rollback();

        // EXTENDED SCOPE
        Map<String, Object> map = new HashMap<>();
        map.put("jakarta.persistence", PessimisticLockScope.EXTENDED);

        EntityManager em3 = getEntityManagerWithOpenTransaction();
        foundCourse = em3.find(PessimisticLockingCourse.class, 1L, LockModeType.PESSIMISTIC_WRITE, map);
        em3.getTransaction()
            .rollback();

        em2.close();
        em3.close();
    }

    protected EntityManager getEntityManagerWithOpenTransaction() throws IOException {
        String propertyFileName = "hibernate-pessimistic-locking.properties";
        if (sessionFactory == null) {
            sessionFactory = HibernateUtil.getSessionFactory(propertyFileName);
        }
        EntityManager entityManager = sessionFactory.openSession();
        entityManager.getTransaction().begin();

        return entityManager;
    }
    
    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
