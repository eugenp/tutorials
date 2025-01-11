package com.baeldung.hibernate.cache;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hibernate.query.NativeQuery;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.hibernate.cache.model.Bar;
import com.baeldung.hibernate.cache.model.Foo;
import com.baeldung.hibernate.cache.model.Roo;
import com.baeldung.hibernate.cache.service.EntityService;
import com.baeldung.spring.PersistenceL2CacheConfig;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceL2CacheConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecondLevelCacheIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityService service;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private Cache cache;

    @Before
    public final void before() {
        cache.evictAll();
    }

    private <T> T doInTx(Supplier<T> sup) {
        return new TransactionTemplate(platformTransactionManager).execute(status -> sup.get());
    }

    @Test
    public final void givenCacheableEntity_whenOneIsUpdatedInJpaQuery_thenAllCacheableEntitiesAreEvicted() {
        final Foo first = randomFoo(2);
        final Foo second = randomFoo(2);

        service.create(first);
        service.create(second);

        boolean cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertTrue(cached);

        doInTx(() -> {
            final Query jpaQuery = entityManager.createQuery("update Foo set name = :updatedName where id = :id");
            jpaQuery.setParameter("updatedName", "newName");
            jpaQuery.setParameter("id", first.getId());

            return jpaQuery.executeUpdate();
        });

        cached = cache.contains(Foo.class, first.getId());
        assertFalse(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertFalse(cached);

        service.findOneFoo(first.getId());
        cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);

        service.findOneFoo(second.getId());
        cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
    }

    @Test
    public final void givenCacheableEntity_whenOneIsUpdatedInNativeQuery_thenAllOthersAreEvicted() {
        final Foo first = randomFoo(2);
        final Foo second = randomFoo(2);

        service.create(first);
        service.create(second);

        boolean cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertTrue(cached);

        doInTx(() -> {

            final Query nativeQuery = entityManager.createNativeQuery("update FOO set NAME = :updatedName where ID = :id");
            nativeQuery.setParameter("updatedName", "newName");
            nativeQuery.setParameter("id", first.getId());

            return nativeQuery.executeUpdate();
        });

        cached = cache.contains(Foo.class, first.getId());
        assertFalse(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertFalse(cached);

        service.findOneFoo(first.getId());
        cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);

        service.findOneFoo(second.getId());
        cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
    }

    @Test
    public final void givenCacheableEntity_whenOneIsUpdatedViaEntityManager_thenTheUpdatedEntityIsReplacedInCache() {
        final Foo first = randomFoo(2);
        final Foo second = randomFoo(2);

        service.create(first);
        service.create(second);

        boolean cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertTrue(cached);

        doInTx(() -> {
            first.setName("newName");

            return entityManager.merge(first);
        });

        cached = cache.contains(Foo.class, first.getId());
        assertTrue(cached);
        cached = cache.contains(Foo.class, second.getId());
        assertTrue(cached);

        Foo updated = service.findOneFoo(first.getId());
        assertTrue(cached);

        assertEquals("newName", updated.getName());
    }

    @Test
    public final void givenCacheableEntity_whenOtherCachebleEntitiesAreUpdatedInNativeQuery_thenAllCacheableEntitiesAreEvicted() {
        final Foo foo = randomFoo(2);

        service.create(foo);

        service.findOneFoo(foo.getId());

        boolean cached = cache.contains(Foo.class, foo.getId());

        assertTrue(cached);

        doInTx(() -> {
            final Bar bar = randomBar(0);
            entityManager.persist(bar);
            final Query nativeQuery = entityManager.createNativeQuery("update BAR set NAME = :updatedName where ID = :id");
            nativeQuery.setParameter("updatedName", "newName");
            nativeQuery.setParameter("id", bar.getId());

            return nativeQuery.executeUpdate();
        });

        cached = cache.contains(Foo.class, foo.getId());

        assertFalse(cached);

        service.findOneFoo(foo.getId());
        cached = cache.contains(Foo.class, foo.getId());
        assertTrue(cached);
    }

    @Test
    public final void givenCacheableEntityIsCreated_whenLoaded_thenItAndItsAssociationsAreLoadedFromSecondLevelCache() {
        final Foo foo = randomFoo(2);
        service.create(foo);
        boolean cached = cache.contains(Foo.class, foo.getId());

        assertTrue(cached);
        assertNotNull(service.findOneFoo(foo.getId()));

        List<Bar> bars = foo.getBars();

        assertEquals(2, bars.size());

        for (Bar bar : bars) {
            cached = cache.contains(Bar.class, bar.getId());
            assertTrue(cached);
            assertEquals(bar, service.findOneBar(bar.getId()));
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void givenCacheableQuery_whenExecutedForCacheableEntities_thenTheyWillCachedForPrimaryKeyLookups() {
        List<Foo> foos = doInTx(() -> {
            return entityManager.createQuery("select f from Foo f")
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        });

        assumeFalse(foos.isEmpty());

        foos.forEach(foo -> {
            boolean cached = cache.contains(Foo.class, foo.getId());

            assertTrue(cached);
        });
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void givenCacheableQuery_whenExecutedForNonCacheableEntities_thenTheyWillNotBeCachedForPrimaryKeyLookups() {
        List<Roo> roos = doInTx(() -> {
            return entityManager.createQuery("select r from Roo r")
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
        });

        assumeFalse(roos.isEmpty());

        roos.forEach(foo -> {
            boolean cached = cache.contains(Roo.class, foo.getId());

            assertFalse(cached);
        });
    }

    @Test
    public final void givenNonCacheableEntity_whenUpdatedInNativeQuery_thenCachedEntitiesAreEvicted() {
        final Foo foo = randomFoo(2);

        service.create(foo);

        service.findOneFoo(foo.getId());

        boolean cached = cache.contains(Foo.class, foo.getId());

        assertTrue(cached);

        doInTx(() -> {
            final Roo roo = new Roo(randomAlphabetic(6));
            entityManager.persist(roo);

            final Query nativeQuery = entityManager.createNativeQuery("update ROO set NAME = :updatedName where ID = :id");
            nativeQuery.setParameter("updatedName", "newName");
            nativeQuery.setParameter("id", roo.getId());

            return nativeQuery.executeUpdate();
        });

        cached = cache.contains(Foo.class, foo.getId());

        assertFalse(cached);

        service.findOneFoo(foo.getId());
        cached = cache.contains(Foo.class, foo.getId());
        assertTrue(cached);
    }

    @Test
    public final void givenNonCacheableEntity_whenUpdatedInNativeQueryAsSynchronizedEntity_thenCachedEntitiesAreNotEvicted() {
        final Foo foo = randomFoo(2);

        service.create(foo);

        service.findOneFoo(foo.getId());

        boolean cached = cache.contains(Foo.class, foo.getId());

        assertTrue(cached);

        doInTx(() -> {
            final Roo roo = new Roo(randomAlphabetic(6));
            entityManager.persist(roo);

            final Query nativeQuery = entityManager.createNativeQuery("update ROO set NAME = :updatedName where ID = :id");
            nativeQuery.setParameter("updatedName", "newName");
            nativeQuery.setParameter("id", roo.getId());

            nativeQuery.unwrap(NativeQuery.class)
            .addSynchronizedEntityClass(Roo.class);

            return nativeQuery.executeUpdate();
        });

        cached = cache.contains(Foo.class, foo.getId());
        assertTrue(cached);

        service.findOneFoo(foo.getId());
        cached = cache.contains(Foo.class, foo.getId());
        assertTrue(cached);
    }

    @Test
    public final void givenNonCacheableEntityIsCreated_whenLoaded_thenItIsFromDatabase() {
        final Roo roo = new Roo(randomAlphabetic(6));
        service.create(roo);
        boolean cached = cache.contains(Roo.class, roo.getId());

        assertFalse(cached);

        assertNotNull(service.findOneFoo(roo.getId()));
    }

    private Bar randomBar(int n) {
        Bar bar = new Bar(randomAlphabetic(6));
        bar.setIndex(n);

        return bar;
    }

    private List<Bar> randomBars(int nbars) {

        return IntStream.range(0, nbars)
            .mapToObj(this::randomBar)
            .collect(Collectors.toList());
    }

    private Foo randomFoo(int nbars) {
        final Foo foo = new Foo(randomAlphabetic(6));

        foo.setBars(randomBars(nbars));

        return foo;
    }
}