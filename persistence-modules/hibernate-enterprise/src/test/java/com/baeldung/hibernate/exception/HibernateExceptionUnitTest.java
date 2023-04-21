package com.baeldung.hibernate.exception;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.sqm.UnknownEntityException;
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
        thrown.expect(isA(MappingException.class));
        thrown.expectMessage("Unable to locate persister: com.baeldung.hibernate.exception.ProductNotMapped");

        ProductNotMapped product = new ProductNotMapped();
        product.setId(1);
        product.setName("test");

        Session session = sessionFactory.openSession();
        session.save(product);
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
        thrown.expectCause(isA(UnknownEntityException.class));
        thrown.expectMessage("Could not resolve root entity 'PRODUCT");

        Session session = sessionFactory.openSession();
        List<Product> results = session.createQuery("from PRODUCT", Product.class)
            .getResultList();
    }

    @Test
    public void givenEntityWithoutId_whenSessionFactoryCreated_thenAnnotationException() {
        thrown.expect(isA(HibernateException.class));
        thrown.expectMessage("Entity 'com.baeldung.hibernate.exception.EntityWithNoId' has no identifier (every '@Entity' class must declare or inherit at least one '@Id' or '@EmbeddedId' property)");

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
        thrown.expectCause(isA(SQLGrammarException.class));
        thrown.expectMessage("could not prepare statement");

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
        thrown.expectCause(isA(SQLGrammarException.class));
        thrown.expectMessage("could not prepare statement");

        Session session = sessionFactory.openSession();
        NativeQuery<Product> query = session.createNativeQuery("select * from NON_EXISTING_TABLE", Product.class);
        query.getResultList();
    }

    @Test
    public void whenDuplicateIdSaved_thenConstraintViolationException() {
        thrown.expectCause(isA(ConstraintViolationException.class));
        thrown.expectMessage("could not execute statement");

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
    public void givenEntityWithoutId_whenCallingSave_thenThrowIdentifierGenerationException() {

        thrown.expect(isA(IdentifierGenerationException.class));
        thrown.expectMessage("ids for this class must be manually assigned before calling save(): com.baeldung.hibernate.exception.Product");

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            ProductEntity product = new ProductEntity();
            product.setName("Product Name");

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
        thrown.expectMessage("could not prepare statement");

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
        thrown.expectCause(isA(StaleStateException.class));
        thrown.expectMessage("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update PRODUCT set description=?, name=? where id=?");

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
        thrown.expect(isA(IllegalStateException.class));
        thrown.expectMessage("Transaction already active");

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product1 = new Product();
            product1.setId(15);
            product1.setName("Product1");
            session.save(product1);
            transaction = session.beginTransaction();
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
