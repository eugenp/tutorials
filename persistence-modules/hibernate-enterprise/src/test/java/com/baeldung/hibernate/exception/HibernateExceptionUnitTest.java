package com.baeldung.hibernate.exception;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.hibernate.AnnotationException;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.query.NativeQuery;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.SchemaManagementException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateExceptionUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(HibernateExceptionUnitTest.class);
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws IOException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Configuration getConfiguration() {
        Configuration cfg = new Configuration();
        cfg.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
        cfg.setProperty(AvailableSettings.HBM2DDL_AUTO, "none");
        cfg.setProperty(AvailableSettings.DRIVER, "org.h2.Driver");
        cfg.setProperty(AvailableSettings.URL, "jdbc:h2:mem:myexceptiondb2;DB_CLOSE_DELAY=-1");
        cfg.setProperty(AvailableSettings.USER, "sa");
        cfg.setProperty(AvailableSettings.PASS, "");
        return cfg;
    }

    @Test
    public void whenQueryExecutedWithUnmappedEntity_thenMappingException() {
        thrown.expectCause(isA(MappingException.class));
        thrown.expectMessage("Unknown entity: java.lang.String");

        Session session = sessionFactory.openSession();
        NativeQuery<String> query = session.createNativeQuery("select name from PRODUCT", String.class);
        query.getResultList();
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void whenQueryExecuted_thenOK() {
        Session session = sessionFactory.openSession();
        NativeQuery query = session.createNativeQuery("select name from PRODUCT");
        List results = query.getResultList();
        assertNotNull(results);
    }

    @Test
    public void whenQueryExecutedWithInvalidClassName_thenQuerySyntaxException() {
        thrown.expectCause(isA(QuerySyntaxException.class));
        thrown.expectMessage("PRODUCT is not mapped [from PRODUCT]");

        Session session = sessionFactory.openSession();
        List<Product> results = session.createQuery("from PRODUCT", Product.class)
            .getResultList();
    }

    @Test
    public void givenEntityWithoutId_whenSessionFactoryCreated_thenAnnotationException() {
        thrown.expect(AnnotationException.class);
        thrown.expectMessage("No identifier specified for entity");

        Configuration cfg = getConfiguration();
        cfg.addAnnotatedClass(EntityWithNoId.class);
        cfg.buildSessionFactory();
    }

    @Test
    public void givenMissingTable_whenSchemaValidated_thenSchemaManagementException() {
        thrown.expect(SchemaManagementException.class);
        thrown.expectMessage("Schema-validation: missing table");

        Configuration cfg = getConfiguration();
        cfg.setProperty(AvailableSettings.HBM2DDL_AUTO, "validate");
        cfg.addAnnotatedClass(Product.class);
        cfg.buildSessionFactory();
    }

    @Test
    public void whenWrongDialectSpecified_thenCommandAcceptanceException() {
        thrown.expect(SchemaManagementException.class);
        thrown.expectCause(isA(CommandAcceptanceException.class));
        thrown.expectMessage("Halting on error : Error executing DDL");

        Configuration cfg = getConfiguration();
        cfg.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
        cfg.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");

        // This does not work due to hibernate bug
        // cfg.setProperty(AvailableSettings.HBM2DDL_HALT_ON_ERROR,"true");
        cfg.getProperties()
            .put(AvailableSettings.HBM2DDL_HALT_ON_ERROR, true);

        cfg.addAnnotatedClass(Product.class);
        cfg.buildSessionFactory();
    }

    @Test
    public void givenMissingTable_whenEntitySaved_thenSQLGrammarException() {
        thrown.expect(isA(PersistenceException.class));
        thrown.expectCause(isA(SQLGrammarException.class));
        thrown.expectMessage("SQLGrammarException: could not prepare statement");

        Configuration cfg = getConfiguration();
        cfg.addAnnotatedClass(Product.class);

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Product product = new Product();
            product.setId(1);
            product.setName("Product 1");
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
            closeSessionFactoryQuietly(sessionFactory);
        }
    }

    @Test
    public void givenMissingTable_whenQueryExecuted_thenSQLGrammarException() {
        thrown.expect(isA(PersistenceException.class));
        thrown.expectCause(isA(SQLGrammarException.class));
        thrown.expectMessage("SQLGrammarException: could not prepare statement");

        Session session = sessionFactory.openSession();
        NativeQuery<Product> query = session.createNativeQuery("select * from NON_EXISTING_TABLE", Product.class);
        query.getResultList();
    }

    @Test
    public void whenDuplicateIdSaved_thenConstraintViolationException() {
        thrown.expect(isA(PersistenceException.class));
        thrown.expectCause(isA(ConstraintViolationException.class));
        thrown.expectMessage("ConstraintViolationException: could not execute statement");

        Session session = null;
        Transaction transaction = null;

        for (int i = 1; i <= 2; i++) {
            try {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                Product product = new Product();
                product.setId(1);
                product.setName("Product " + i);
                session.save(product);
                transaction.commit();
            } catch (Exception e) {
                rollbackTransactionQuietly(transaction);
                throw (e);
            } finally {
                closeSessionQuietly(session);
            }
        }
    }

    @Test
    public void givenNotNullPropertyNotSet_whenEntityIdSaved_thenPropertyValueException() {
        thrown.expect(isA(PropertyValueException.class));
        thrown.expectMessage("not-null property references a null or transient value");

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product = new Product();
            product.setId(1);
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }

    }

    @Test
    public void givenQueryWithDataTypeMismatch_WhenQueryExecuted_thenDataException() {
        thrown.expectCause(isA(DataException.class));
        thrown.expectMessage("org.hibernate.exception.DataException: could not prepare statement");

        Session session = sessionFactory.openSession();
        NativeQuery<Product> query = session.createNativeQuery("select * from PRODUCT where id='wrongTypeId'", Product.class);
        query.getResultList();
    }

    @Test
    public void givenSessionContainingAnId_whenIdAssociatedAgain_thenNonUniqueObjectException() {
        thrown.expect(isA(NonUniqueObjectException.class));
        thrown.expectMessage("A different object with the same identifier value was already associated with the session");

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(1);
            product1.setName("Product 1");
            session.save(product1);

            Product product2 = new Product();
            product2.setId(1);
            product2.setName("Product 2");
            session.save(product2);

            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }
    }

    @Test
    public void whenDeletingADeletedObject_thenOptimisticLockException() {
        thrown.expect(isA(OptimisticLockException.class));
        thrown.expectMessage("Batch update returned unexpected row count from update");
        thrown.expectCause(isA(StaleStateException.class));

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(12);
            product1.setName("Product 12");
            session.save(product1);
            transaction.commit();
            session.close();

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Product product2 = session.get(Product.class, 12);
            session.createNativeQuery("delete from Product where id=12")
                .executeUpdate();
            // We need to refresh to fix the error.
            // session.refresh(product2);
            session.delete(product2);
            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }
    }

    @Test
    public void whenUpdatingNonExistingObject_thenStaleStateException() {
        thrown.expect(isA(OptimisticLockException.class));
        thrown.expectMessage("Row was updated or deleted by another transaction");
        thrown.expectCause(isA(StaleObjectStateException.class));

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(15);
            product1.setName("Product1");
            session.update(product1);
            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }
    }

    @Test
    public void givenTxnMarkedRollbackOnly_whenCommitted_thenTransactionException() {
        thrown.expect(isA(TransactionException.class));

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(15);
            product1.setName("Product1");
            session.save(product1);
            transaction.setRollbackOnly();

            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }
    }

    private void rollbackTransactionQuietly(Transaction transaction) {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                logger.error("Exception while rolling back transaction", e);
            }
        }
    }

    private void closeSessionQuietly(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                logger.error("Exception while closing session", e);
            }
        }
    }

    private void closeSessionFactoryQuietly(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (Exception e) {
                logger.error("Exception while closing sessionFactory", e);
            }
        }
    }

    @Test
    public void givenExistingEntity_whenIdUpdated_thenHibernateException() {
        thrown.expect(isA(PersistenceException.class));
        thrown.expectCause(isA(HibernateException.class));
        thrown.expectMessage("identifier of an instance of com.baeldung.hibernate.exception.Product was altered");

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(222);
            product1.setName("Product 222");
            session.save(product1);
            transaction.commit();
            closeSessionQuietly(session);

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product2 = session.get(Product.class, 222);
            product2.setId(333);
            session.save(product2);

            transaction.commit();
        } catch (Exception e) {
            rollbackTransactionQuietly(transaction);
            throw (e);
        } finally {
            closeSessionQuietly(session);
        }
    }
}
