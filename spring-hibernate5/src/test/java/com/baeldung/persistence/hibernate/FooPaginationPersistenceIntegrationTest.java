package com.baeldung.persistence.hibernate;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IFooService;
import com.baeldung.spring.PersistenceConfig;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooPaginationPersistenceIntegrationTest {

    @Autowired
    private IFooService fooService;

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    // tests

    @Before
    public final void before() {
        final int minimalNumberOfEntities = 25;
        if (fooService.findAll().size() <= minimalNumberOfEntities) {
            for (int i = 0; i < minimalNumberOfEntities; i++) {
                fooService.create(new Foo(randomAlphabetic(6)));
            }
        }

        session = sessionFactory.openSession();
    }

    @After
    public final void after() {
        session.close();
    }

    // tests

    @Test
    public final void whenContextIsBootstrapped_thenNoExceptions() {
        //
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void whenRetrievingPaginatedEntities_thenCorrectSize() {
        final int pageNumber = 1;
        final int pageSize = 10;
        final List<Foo> fooList = session.createQuery("From Foo").setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();
        assertThat(fooList, hasSize(pageSize));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void whenRetrievingAllPages_thenCorrect() {
        int pageNumber = 1;
        final int pageSize = 10;

        final String countQ = "Select count (f.id) from Foo f";
        final Long countResult = (Long) session.createQuery(countQ).uniqueResult();

        final List<Foo> fooList = Lists.newArrayList();
        int totalEntities = 0;
        while (totalEntities < countResult) {
            fooList.addAll(session.createQuery("From Foo").setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList());
            totalEntities = fooList.size();
            pageNumber++;
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void whenRetrievingLastPage_thenCorrectSize() {
        final int pageSize = 10;

        final String countQ = "Select count (f.id) from Foo f";
        final Long countResults = (Long) session.createQuery(countQ).uniqueResult();
        final int lastPageNumber = (int) ((countResults / pageSize) + 1);

        final List<Foo> lastPage = session.createQuery("From Foo").setFirstResult((lastPageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();

        assertThat(lastPage, hasSize(lessThan(pageSize + 1)));
    }

    // testing - scrollable

    @Test
    public final void givenUsingTheScrollableApi_whenRetrievingPaginatedData_thenCorrect() {
        final int pageSize = 10;
        final String hql = "FROM Foo f order by f.name";

        final ScrollableResults resultScroll = session.createQuery(hql).scroll(ScrollMode.FORWARD_ONLY);

        // resultScroll.last();
        // final int totalResults = resultScroll.getRowNumber() + 1;

        resultScroll.first();
        resultScroll.scroll(0);
        final List<Foo> fooPage = Lists.newArrayList();
        int i = 0;
        while (pageSize > i++) {
            fooPage.add((Foo) resultScroll.get(0));
            if (!resultScroll.next()) {
                break;
            }
        }

        assertThat(fooPage, hasSize(lessThan(10 + 1)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void givenUsingTheCriteriaApi_whenRetrievingFirstPage_thenCorrect() {
        final int pageSize = 10;

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Foo> criteriaItem = builder.createQuery(Foo.class);
        Root<Foo> rootItem = criteriaItem.from(Foo.class);
        criteriaItem.select(rootItem);
        final List<Foo> firstPage = session.createQuery(criteriaItem).setFirstResult(0).setMaxResults(pageSize).getResultList();

        assertThat(firstPage, hasSize(pageSize));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void givenUsingTheCriteriaApi_whenRetrievingPaginatedData_thenCorrect() {

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaItem = builder.createQuery(Tuple.class);
        Root<Foo> rootItem = criteriaItem.from(Foo.class);
        criteriaItem.multiselect(builder.count(rootItem));
        final List<Tuple> itemProjected = session.createQuery(criteriaItem).getResultList();
        final Long count = (Long) itemProjected.get(0).get(0);

        int pageNumber = 1;
        final int pageSize = 10;
        final List<Foo> fooList = Lists.newArrayList();

        CriteriaBuilder builderFoo = session.getCriteriaBuilder();
        CriteriaQuery<Foo> criteriaFoo = builderFoo.createQuery(Foo.class);
        Root<Foo> rootFoo = criteriaFoo.from(Foo.class);
        criteriaFoo.select(rootFoo);

        int totalEntities = 0;
        while (totalEntities < count.intValue()) {
            fooList.addAll(session.createQuery(criteriaFoo).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList());
            totalEntities = fooList.size();
            pageNumber++;
        }
    }

}
