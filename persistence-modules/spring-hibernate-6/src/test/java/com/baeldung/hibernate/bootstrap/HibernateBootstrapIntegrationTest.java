package com.baeldung.hibernate.bootstrap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.hibernate.bootstrap.model.TestEntity;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@ContextConfiguration(classes = { HibernateXMLConf.class })
@ExtendWith(SpringExtension.class)
class HibernateBootstrapIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void whenBootstrapHibernateSession_thenNoException() {

        Session session = sessionFactory.getCurrentSession();

        TestEntity newEntity = new TestEntity();
        newEntity.setId(1);
        session.save(newEntity);

        TestEntity searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);
    }

    @Test
    void whenProgrammaticTransactionCommit_thenEntityIsInDatabase() {
        assertTrue(TestTransaction.isActive());

        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();

        TestEntity newEntity = new TestEntity();
        newEntity.setId(1);
        session.save(newEntity);

        TestEntity searchEntity = session.find(TestEntity.class, 1);


        assertTrue(TestTransaction.isFlaggedForRollback());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertFalse(TestTransaction.isFlaggedForRollback());
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it, but don't commit.
        TestTransaction.start();

        assertTrue(TestTransaction.isFlaggedForRollback());
        assertTrue(TestTransaction.isActive());

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);

        session.delete(searchEntity);
        session.flush();

        TestTransaction.end();

        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);

        session.delete(searchEntity);
        session.flush();

        assertTrue(TestTransaction.isActive());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        assertFalse(TestTransaction.isActive());

        //Check that the entity is no longer there in a new transaction.
        TestTransaction.start();

        assertTrue(TestTransaction.isActive());

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNull(searchEntity);
    }

    @Test
    @Commit
    void givenTransactionCommitDefault_whenProgrammaticTransactionCommit_thenEntityIsInDatabase() {
        assertTrue(TestTransaction.isActive());

        //Save an entity and commit.
        Session session = sessionFactory.getCurrentSession();

        TestEntity newEntity = new TestEntity();
        newEntity.setId(1);
        session.save(newEntity);

        TestEntity searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);
        assertFalse(TestTransaction.isFlaggedForRollback());

        TestTransaction.end();

        assertFalse(TestTransaction.isFlaggedForRollback());
        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it, but don't commit.
        TestTransaction.start();

        assertFalse(TestTransaction.isFlaggedForRollback());
        assertTrue(TestTransaction.isActive());

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);

        session.delete(searchEntity);
        session.flush();

        TestTransaction.flagForRollback();
        TestTransaction.end();

        assertFalse(TestTransaction.isActive());

        //Check that the entity is still there in a new transaction,
        //then delete it and commit.
        TestTransaction.start();

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNotNull(searchEntity);

        session.delete(searchEntity);
        session.flush();

        assertTrue(TestTransaction.isActive());

        TestTransaction.end();

        assertFalse(TestTransaction.isActive());

        //Check that the entity is no longer there in a new transaction.
        TestTransaction.start();

        assertTrue(TestTransaction.isActive());

        session = sessionFactory.getCurrentSession();
        searchEntity = session.find(TestEntity.class, 1);

        assertNull(searchEntity);
    }

}
