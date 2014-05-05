package org.baeldung.persistence.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.model.Foo;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FooServiceSortingTests {
    private EntityManager entityManager;

    @BeforeClass
    public static void before() {
        //
    }

    @After
    public final void after() {
        //
    }

    @Test
    public final void whenSortingByOneAttributeDefaultOrder_thenPrintSortedResult() {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        final String jql = "Select f from Foo as f order by f.id";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertEquals(1, fooList.get(0).getId());
        assertEquals(100, fooList.get(fooList.toArray().length - 1).getId());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingByOneAttributeSetOrder_thenSortedPrintResult() {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        final String jql = "Select f from Foo as f order by f.id desc";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertEquals(100, fooList.get(0).getId());
        assertEquals(1, fooList.get(fooList.toArray().length - 1).getId());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortingByTwoAttributes_thenPrintSortedResult() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        final String jql = "Select f from Foo as f order by f.name asc, f.id desc";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getId());
        }
    }

    @Test
    public final void whenSortinfBar_thenPrintBarsSortedWithFoos() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
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
    public final void whenSortingByStringNullLast_thenLastNull() {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        final String jql = "Select f from Foo as f order by f.name desc NULLS LAST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

    // @Test
    // public final void whenSortingByStringNullFirst_thenFirstNull() {
    // final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
    // final EntityManager entityManager = emf.createEntityManager();
    // final EntityTransaction entityTransaction = entityManager.getTransaction();
    // entityTransaction.begin();
    // final String jql = "Select f from Foo as f order by f.name desc NULLS FIRST";
    // final Query sortQuery = entityManager.createQuery(jql);
    // final List<Foo> fooList = sortQuery.getResultList();
    // assertNull(fooList.get(0).getName());
    // for (final Foo foo : fooList) {
    // System.out.println("Name:" + foo.getName() + "-------Id:" + foo.getTest_Null());
    // }
    // }

    @Test
    public final void whenSortingByIntNull_thenException() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        final String jql = "Select f from Foo as f order by f.test_Null desc NULLS FIRST";
        final Query sortQuery = entityManager.createQuery(jql);
        boolean thrown = false;
        try {
            final List<Foo> fooList = sortQuery.getResultList();
        } catch (final javax.persistence.PersistenceException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public final void whenSortingFooWithCriteria_thenPrintSortedFoos() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
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
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("punit");
        final EntityManager entityManager = emf.createEntityManager();
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
