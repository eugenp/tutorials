package org.baeldung.persistence.service;

import static org.junit.Assert.*;
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
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cc.jpa.example.Foo;
import com.cc.jpa.example.Bar;

public class FooServiceSortingTests {
    private static EntityManager entityManager;
    private static EntityManagerFactory emf;
    private static EntityTransaction entityTransaction;
    private static CriteriaBuilder criteriaBuilder;

    @BeforeClass
    public static void before() {

        emf = Persistence.createEntityManagerFactory("punit");
        entityManager = emf.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

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
            System.out.println("Name:" + foo.getName() + "-------BarId:" + foo.getBar().getId());
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
    public final void whenSortingByStringNullLast_thenLastNull() {

        final String jql = "Select f from Foo as f order by f.name desc NULLS LAST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

    @Test
    public final void whenSortingByStringNullFirst_thenFirstNull() {

        final Foo nullNameFoo = new Foo();
        nullNameFoo.setName(null);

        final Bar bar = new Bar();
        final List<Foo> fooList1 = Lists.newArrayList();
        bar.setName("Bar_Me");
        nullNameFoo.setBar(bar);
        fooList1.add(nullNameFoo);
        bar.setFooList(fooList1);
        entityManager.persist(bar);
        entityManager.persist(nullNameFoo);
        entityTransaction.commit();
        final String jql = "Select f from Foo as f order by f.name desc NULLS FIRST";
        final Query sortQuery = entityManager.createQuery(jql);
        final List<Foo> fooList = sortQuery.getResultList();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());
        }
    }

    @Test
    public final void whenSortingFooWithCriteria_thenPrintSortedFoos() {

        criteriaBuilder = entityManager.getCriteriaBuilder();
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

        criteriaBuilder = entityManager.getCriteriaBuilder();
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
