package org.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.baeldung.config.PersistenceJPAConfig;
import org.baeldung.config.PersistenceJPAConfigL2Cache;
import org.baeldung.persistence.model.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfigL2Cache.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
public class FooServiceSortingWitNullsManualIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FooService service;

    // tests

    @SuppressWarnings("unchecked")
    @Test
    public final void whenSortingByStringNullLast_thenLastNull() {
        service.create(new Foo());
        service.create(new Foo(randomAlphabetic(6)));

        final String jql = "Select f from Foo as f order by f.name desc NULLS LAST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void whenSortingByStringNullFirst_thenFirstNull() {
        service.create(new Foo());

        final String jql = "Select f from Foo as f order by f.name desc NULLS FIRST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

}
