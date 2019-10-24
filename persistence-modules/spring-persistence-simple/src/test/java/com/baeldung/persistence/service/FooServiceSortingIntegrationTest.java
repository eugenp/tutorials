package com.baeldung.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.baeldung.config.PersistenceJPAConfig;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext
@SuppressWarnings("unchecked")
public class FooServiceSortingIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    // tests

    @Test
    public final void whenSortingByOneAttributeDefaultOrder_thenPrintSortedResult() {
        final String jql = "Select f from Foo as f order by f.id";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingByOneAttributeSetOrder_thenSortedPrintResult() {
        final String jql = "Select f from Foo as f order by f.id desc";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingByTwoAttributes_thenPrintSortedResult() {
        final String jql = "Select f from Foo as f order by f.name asc, f.id desc";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingFooByBar_thenBarsSorted() {
        final String jql = "Select f from Foo as f order by f.name, f.bar.id";
        final Query barJoinQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = barJoinQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
            if (foo.getBar() != null) {
                System.out.print("-------BarId:" + foo.getBar().getId());
            }
        }
    }

    @Test
    public final void whenSortinfBar_thenPrintBarsSortedWithFoos() {
        final String jql = "Select b from Bar as b order by b.id";
        final Query barQuery = entityManager.createQuery(jql);
        final List<Bar> barList = barQuery.getResultList();
        for (final Bar bar : barList) {
            System.out.println("Bar Id:" + bar.getId());
            for (final Foo foo : bar.getFooList()) {
                System.out.println("FooName:" + foo.getName());
            }
        }
    }

    @Test
    public final void whenSortingFooWithCriteria_thenPrintSortedFoos() {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Foo> criteriaQuery = criteriaBuilder.createQuery(Foo.class);
        final Root<Foo> from = criteriaQuery.from(Foo.class);
        final CriteriaQuery<Foo> select = criteriaQuery.select(from);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")));
        final TypedQuery<Foo> typedQuery = entityManager.createQuery(select);
        final List<Foo> fooList = typedQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "--------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingFooWithCriteriaAndMultipleAttributes_thenPrintSortedFoos() {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Foo> criteriaQuery = criteriaBuilder.createQuery(Foo.class);
        final Root<Foo> from = criteriaQuery.from(Foo.class);
        final CriteriaQuery<Foo> select = criteriaQuery.select(from);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get("name")), criteriaBuilder.desc(from.get("id")));
        final TypedQuery<Foo> typedQuery = entityManager.createQuery(select);
        final List<Foo> fooList = typedQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

}
