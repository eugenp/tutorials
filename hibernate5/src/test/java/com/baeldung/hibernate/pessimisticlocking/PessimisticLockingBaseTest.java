package com.baeldung.hibernate.pessimisticlocking;

import com.baeldung.hibernate.HibernateUtil;

import javax.persistence.EntityManager;
import java.io.IOException;

abstract public class PessimisticLockingBaseTest {

    protected static EntityManager getEntityManagerWithOpenTransaction() throws IOException {
        String propertyFileName = "hibernate-pessimistic-locking.properties";
        EntityManager entityManager = 
            HibernateUtil.getSessionFactory(propertyFileName).openSession();
        entityManager.getTransaction().begin();
        
        return entityManager;
    }

}
