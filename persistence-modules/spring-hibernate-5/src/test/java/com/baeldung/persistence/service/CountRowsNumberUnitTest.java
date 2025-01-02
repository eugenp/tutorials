package com.baeldung.persistence.service;

import com.baeldung.hibernate.cache.model.Bar;
import com.baeldung.hibernate.cache.model.Foo;
import com.baeldung.hibernate.cache.service.FooService;
import com.baeldung.spring.PersistenceJPAConfigL2Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceJPAConfigL2Cache.class}, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
public class CountRowsNumberUnitTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FooService fooService;

    @Before
    public final void before() {
        entityManager.getEntityManagerFactory().getCache().evictAll();
    }

    @Test
    public void givenFooObject_whenCountingAllRows_thenShouldReturnCount() {
        final Foo foo = new Foo(randomAlphabetic(6));
        fooService.create(foo);

        long count = fooService.countAllRowsUsingHibernateCriteria();

        assertEquals(1, count);
    }

    @Test
    @Transactional
    public void givenTwoBarsAndFooObjects_whenCountingFooByBarName_thenShouldReturnCorrectCounts() {
        Bar bar1 = new Bar("bar1");
        Bar bar2 = new Bar("bar2");

        Foo foo1 = new Foo(randomAlphabetic(6));
        Foo foo2 = new Foo(randomAlphabetic(6));
        Foo foo3 = new Foo(randomAlphabetic(6));

        foo1.setBar(bar1);
        foo2.setBar(bar1);
        foo3.setBar(bar2);

        fooService.create(foo1);
        fooService.create(foo2);
        fooService.create(foo3);

        long countByBar1 = fooService.getFooCountByBarNameUsingHibernateCriteria(bar1.getName());
        long countByBar2 = fooService.getFooCountByBarNameUsingHibernateCriteria(bar2.getName());

        assertEquals(2, countByBar1);
        assertEquals(1, countByBar2);
    }

    @Test
    @Transactional
    public void givenBarsAndFooObjects_whenCountingFooByBarNameAndFooName_thenShouldReturnCorrectCounts() {
        Bar bar1 = new Bar("bar1");
        Bar bar2 = new Bar("bar2");

        Foo foo1 = new Foo("foo1");
        Foo foo2 = new Foo("foo2");
        Foo foo1Duplicate = new Foo("foo1"); // Duplicate Foo name with a different Bar

        foo1.setBar(bar1);
        foo2.setBar(bar2);
        foo1Duplicate.setBar(bar1);

        fooService.create(foo1);
        fooService.create(foo2);
        fooService.create(foo1Duplicate);

        long countFoo1Bar1 = fooService.getFooCountByBarNameAndFooNameUsingHibernateCriteria(bar1.getName(), "foo1");
        long countFoo2Bar1 = fooService.getFooCountByBarNameAndFooNameUsingHibernateCriteria(bar1.getName(), "foo2");
        long countFoo2Bar2 = fooService.getFooCountByBarNameAndFooNameUsingHibernateCriteria(bar2.getName(), "foo2");

        assertEquals(2, countFoo1Bar1); // One Foo named "foo1" belongs to Bar "bar1"
        assertEquals(0, countFoo2Bar1); // One Foo named "foo2" belongs to Bar "bar1"
        assertEquals(1, countFoo2Bar2); // One Foo named "foo1" belongs to Bar "bar2"
    }

}
