package com.baeldung.jpa.storedprocedure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Persistence;
import jakarta.persistence.StoredProcedureQuery;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.jpa.model.Car;

public class StoredProcedureLiveTest {

    private static EntityManagerFactory factory = null;
    private static EntityManager entityManager = null;

    @BeforeClass
    public static void init() {
        factory = Persistence.createEntityManagerFactory("jpa-db");
        entityManager = factory.createEntityManager();
    }

    @Before
    public void setup() {
    }

    @Test
    public void createCarTest() {
        final EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            final Car car = new Car("Fiat Marea", 2015);
            entityManager.persist(car);
            transaction.commit();
        } catch (final Exception e) {
            System.out.println(e.getCause());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Test
    public void findCarsByYearNamedProcedure() {
        final StoredProcedureQuery findByYearProcedure = entityManager.createNamedStoredProcedureQuery("findByYearProcedure");
        final StoredProcedureQuery storedProcedure = findByYearProcedure.setParameter("p_year", 2015);
        storedProcedure.getResultList()
            .forEach(c -> Assert.assertEquals(new Integer(2015), ((Car) c).getYear()));
    }

    @Test
    public void findCarsByYearNoNamed() {
        final StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("FIND_CAR_BY_YEAR", Car.class)
            .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
            .setParameter(1, 2015);
        storedProcedure.getResultList()
            .forEach(c -> Assert.assertEquals(new Integer(2015), ((Car) c).getYear()));
    }

    @AfterClass
    public static void destroy() {

        if (entityManager != null) {
            entityManager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }
}
