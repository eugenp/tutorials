package com.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.hibernate.cache.model.Bar;
import com.baeldung.hibernate.cache.model.Foo;
import com.baeldung.hibernate.cache.service.FooService;
import com.baeldung.spring.PersistenceJPAConfigL2Cache;

import net.sf.ehcache.CacheManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfigL2Cache.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
public class SecondLevelCacheIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FooService fooService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Before
    public final void before() {
        entityManager.getEntityManagerFactory().getCache().evictAll();
    }

    @Test
    public final void givenEntityIsLoaded_thenItIsCached() {
        final Foo foo = new Foo(randomAlphabetic(6));
        fooService.create(foo);
        fooService.findOne(foo.getId());
        final int size = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.baeldung.hibernate.cache.model.Foo").getSize();
        assertThat(size, greaterThan(0));
    }

    @Test
    public final void givenBarIsUpdatedInNativeQuery_thenFoosAreNotEvicted() {
        final Foo foo = new Foo(randomAlphabetic(6));
        fooService.create(foo);
        fooService.findOne(foo.getId());

        new TransactionTemplate(platformTransactionManager).execute(status -> {
            final Bar bar = new Bar(randomAlphabetic(6));
            entityManager.persist(bar);
            final Query nativeQuery = entityManager.createNativeQuery("update BAR set NAME = :updatedName where ID = :id");
            nativeQuery.setParameter("updatedName", "newName");
            nativeQuery.setParameter("id", bar.getId());
            nativeQuery.unwrap(org.hibernate.SQLQuery.class).addSynchronizedEntityClass(Bar.class);
            return nativeQuery.executeUpdate();
        });

        final int size = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("com.baeldung.hibernate.cache.model.Foo").getSize();
        assertThat(size, greaterThan(0));
    }

    @Test
    public final void givenCacheableQueryIsExecuted_thenItIsCached() {
        new TransactionTemplate(platformTransactionManager).execute(status -> {
            return entityManager.createQuery("select f from Foo f").setHint("org.hibernate.cacheable", true).getResultList();
        });

        final int size = CacheManager.ALL_CACHE_MANAGERS.get(0).getCache("org.hibernate.cache.internal.StandardQueryCache").getSize();
        assertThat(size, greaterThan(0));
    }
}
