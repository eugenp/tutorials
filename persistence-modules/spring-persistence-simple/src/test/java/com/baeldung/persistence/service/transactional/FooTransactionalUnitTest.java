package com.baeldung.persistence.service.transactional;

import com.baeldung.persistence.model.Foo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTransactionalTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
public class FooTransactionalUnitTest {

    static abstract class BasicFooDao {
        @PersistenceContext private EntityManager entityManager;

        public Foo findOne(final long id) {
            return entityManager.find(Foo.class, id);
        }

        public Foo create(final Foo entity) {
            entityManager.persist(entity);
            return entity;
        }
    }

    @Repository
    static class RequiredTransactionalFooDao extends BasicFooDao {
        @Override
        @Transactional(propagation = Propagation.REQUIRED)
        public Foo create(Foo entity) {
            return super.create(entity);
        }
    }

    @Repository
    static class RequiresNewTransactionalFooDao extends BasicFooDao {
        @Override
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public Foo create(Foo entity) {
            return super.create(entity);
        }
    }

    @Repository
    static class SupportTransactionalFooDao extends BasicFooDao {
        @Override
        @Transactional(propagation = Propagation.SUPPORTS)
        public Foo create(Foo entity) {
            return super.create(entity);
        }
    }

    @Repository
    static class MandatoryTransactionalFooDao extends BasicFooDao {
        @Override
        @Transactional(propagation = Propagation.MANDATORY)
        public Foo create(Foo entity) {
            return super.create(entity);
        }
    }

    @Repository
    static class SupportTransactionalFooService {
        @Transactional(propagation = Propagation.SUPPORTS)
        public Foo identity(Foo entity) {
            return entity;
        }
    }

    @Service
    static class MandatoryTransactionalFooService {
        @Transactional(propagation = Propagation.MANDATORY)
        public Foo identity(Foo entity) {
            return entity;
        }
    }

    @Service
    static class NotSupportedTransactionalFooService {
        @Transactional(propagation = Propagation.NOT_SUPPORTED)
        public Foo identity(Foo entity) {
            return entity;
        }
    }

    @Service
    static class NeverTransactionalFooService {
        @Transactional(propagation = Propagation.NEVER)
        public Foo identity(Foo entity) {
            return entity;
        }
    }

    @Autowired private TransactionTemplate transactionTemplate;

    @Autowired private RequiredTransactionalFooDao requiredTransactionalFooDao;

    @Autowired private RequiresNewTransactionalFooDao requiresNewTransactionalFooDao;

    @Autowired private SupportTransactionalFooDao supportTransactionalFooDao;

    @Autowired private MandatoryTransactionalFooDao mandatoryTransactionalFooDao;

    @Autowired private MandatoryTransactionalFooService mandatoryTransactionalFooService;

    @Autowired private NeverTransactionalFooService neverTransactionalFooService;

    @Autowired private NotSupportedTransactionalFooService notSupportedTransactionalFooService;

    @Autowired private SupportTransactionalFooService supportTransactionalFooService;

    @After
    public void tearDown(){
        PersistenceTransactionalTestConfig.clearSpy();
    }

    @Test
    public void givenRequiredWithNoActiveTransaction_whenCallCreate_thenExpect1NewAnd0Suspend() {
        requiredTransactionalFooDao.create(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }



    @Test
    public void givenRequiresNewWithNoActiveTransaction_whenCallCreate_thenExpect1NewAnd0Suspend() {
        requiresNewTransactionalFooDao.create(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

    @Test
    public void givenSupportWithNoActiveTransaction_whenCallService_thenExpect0NewAnd0Suspend() {
        supportTransactionalFooService.identity(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(0, transactionSpy.getCreate());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void givenMandatoryWithNoActiveTransaction_whenCallService_thenExpectIllegalTransactionStateExceptionWith0NewAnd0Suspend() {
        mandatoryTransactionalFooService.identity(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(0, transactionSpy.getCreate());
    }

    @Test
    public void givenNotSupportWithNoActiveTransaction_whenCallService_thenExpect0NewAnd0Suspend() {
        notSupportedTransactionalFooService.identity(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(0, transactionSpy.getCreate());
    }

    @Test
    public void givenNeverWithNoActiveTransaction_whenCallService_thenExpect0NewAnd0Suspend() {
        neverTransactionalFooService.identity(new Foo("baeldung"));
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(0, transactionSpy.getCreate());
    }

    @Test
    public void givenRequiredWithActiveTransaction_whenCallCreate_thenExpect0NewAnd0Suspend() {
        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return requiredTransactionalFooDao.create(foo);
        });
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

    @Test
    public void givenRequiresNewWithActiveTransaction_whenCallCreate_thenExpect1NewAnd1Suspend() {
        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return requiresNewTransactionalFooDao.create(foo);
        });
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(1, transactionSpy.getSuspend());
        Assert.assertEquals(2, transactionSpy.getCreate());
    }

    @Test
    public void givenSupportWithActiveTransaction_whenCallCreate_thenExpect0NewAnd0Suspend() {
        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return supportTransactionalFooDao.create(foo);
        });
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

    @Test
    public void givenMandatoryWithActiveTransaction_whenCallCreate_thenExpect0NewAnd0Suspend() {

        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return mandatoryTransactionalFooDao.create(foo);
        });

        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

    @Test
    public void givenNotSupportWithActiveTransaction_whenCallCreate_thenExpect0NewAnd1Suspend() {
        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return notSupportedTransactionalFooService.identity(foo);
        });

        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(1, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

    @Test(expected = IllegalTransactionStateException.class)
    public void givenNeverWithActiveTransaction_whenCallCreate_thenExpectIllegalTransactionStateExceptionWith0NewAnd0Suspend() {
        transactionTemplate.execute(status -> {
            Foo foo = new Foo("baeldung");
            return neverTransactionalFooService.identity(foo);
        });
        PersistenceTransactionalTestConfig.TransactionSynchronizationAdapterSpy transactionSpy = PersistenceTransactionalTestConfig.getSpy();
        Assert.assertEquals(0, transactionSpy.getSuspend());
        Assert.assertEquals(1, transactionSpy.getCreate());
    }

}
