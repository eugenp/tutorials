package com.baeldung.hibernate.criteria;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;
import com.baeldung.hibernate.criteria.view.ApplicationView;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public class HibernateCriteriaIntegrationTest {

    final private ApplicationView av = new ApplicationView();

    @Test
    @Ignore
    public void testPerformanceOfCriteria() {
        assertFalse(av.checkIfCriteriaTimeLower());
    }

    @Test
    public void testLikeCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedLikeList = session.createQuery("From Item where itemName like '%chair%'").list();
        final String expectedLikeItems[] = new String[expectedLikeList.size()];
        for (int i = 0; i < expectedLikeList.size(); i++) {
            expectedLikeItems[i] = expectedLikeList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedLikeItems, av.likeCriteria());
    }

    @Test
    public void testILikeCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedChairCaseList = session.createQuery("From Item where itemName like '%Chair%'").list();
        final String expectedChairCaseItems[] = new String[expectedChairCaseList.size()];
        for (int i = 0; i < expectedChairCaseList.size(); i++) {
            expectedChairCaseItems[i] = expectedChairCaseList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedChairCaseItems, av.likeCaseCriteria());
    }

    @Test
    public void testNullCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedIsNullDescItemsList = session.createQuery("From Item where itemDescription is null").list();
        final String expectedIsNullDescItems[] = new String[expectedIsNullDescItemsList.size()];
        for (int i = 0; i < expectedIsNullDescItemsList.size(); i++) {
            expectedIsNullDescItems[i] = expectedIsNullDescItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedIsNullDescItems, av.nullCriteria());
    }

    @Test
    public void testIsNotNullCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedIsNotNullDescItemsList = session.createQuery("From Item where itemDescription is not null").list();
        final String expectedIsNotNullDescItems[] = new String[expectedIsNotNullDescItemsList.size()];
        for (int i = 0; i < expectedIsNotNullDescItemsList.size(); i++) {
            expectedIsNotNullDescItems[i] = expectedIsNotNullDescItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedIsNotNullDescItems, av.notNullCriteria());

    }

    @Test
    public void testAverageProjection() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Double> expectedAvgProjItemsList = session.createQuery("Select avg(itemPrice) from Item item").list();

        final Double expectedAvgProjItems[] = new Double[expectedAvgProjItemsList.size()];
        for (int i = 0; i < expectedAvgProjItemsList.size(); i++) {
            expectedAvgProjItems[i] = expectedAvgProjItemsList.get(i);
        }
        session.close();
        assertArrayEquals(expectedAvgProjItems, av.projectionAverage());

    }

    @Test
    public void testRowCountProjection() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Long> expectedCountProjItemsList = session.createQuery("Select count(*) from Item").list();
        final Long expectedCountProjItems[] = new Long[expectedCountProjItemsList.size()];
        for (int i = 0; i < expectedCountProjItemsList.size(); i++) {
            expectedCountProjItems[i] = expectedCountProjItemsList.get(i);
        }
        session.close();
        assertArrayEquals(expectedCountProjItems, av.projectionRowCount());
    }

    @Test
    public void testOrCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedOrCritItemsList = session.createQuery("From Item where itemPrice>1000 or itemName like 'Chair%'").list();
        final String expectedOrCritItems[] = new String[expectedOrCritItemsList.size()];
        for (int i = 0; i < expectedOrCritItemsList.size(); i++) {
            expectedOrCritItems[i] = expectedOrCritItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedOrCritItems, av.orLogicalCriteria());
    }

    @Test
    public void testAndCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedAndCritItemsList = session.createQuery("From Item where itemPrice>1000 and itemName like 'Chair%'").list();
        final String expectedAndCritItems[] = new String[expectedAndCritItemsList.size()];
        for (int i = 0; i < expectedAndCritItemsList.size(); i++) {
            expectedAndCritItems[i] = expectedAndCritItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedAndCritItems, av.andLogicalCriteria());
    }

    @Test
    public void testMultiCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedMultiCritItemsList = session.createQuery("From Item where itemDescription is null and itemName like'chair%'").list();
        final String expectedMultiCritItems[] = new String[expectedMultiCritItemsList.size()];
        for (int i = 0; i < expectedMultiCritItemsList.size(); i++) {
            expectedMultiCritItems[i] = expectedMultiCritItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedMultiCritItems, av.twoCriteria());
    }

    @Test
    public void testSortCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedSortCritItemsList = session.createQuery("From Item order by itemName asc, itemPrice desc").list();
        final String expectedSortCritItems[] = new String[expectedSortCritItemsList.size()];
        for (int i = 0; i < expectedSortCritItemsList.size(); i++) {
            expectedSortCritItems[i] = expectedSortCritItemsList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedSortCritItems, av.sortingCriteria());
    }

    @Test
    public void testGreaterThanCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedGreaterThanList = session.createQuery("From Item where itemPrice>1000").list();
        final String expectedGreaterThanItems[] = new String[expectedGreaterThanList.size()];
        for (int i = 0; i < expectedGreaterThanList.size(); i++) {
            expectedGreaterThanItems[i] = expectedGreaterThanList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedGreaterThanItems, av.greaterThanCriteria());
    }

    @Test
    public void testLessThanCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedLessList = session.createQuery("From Item where itemPrice<1000").list();
        final String expectedLessThanItems[] = new String[expectedLessList.size()];
        for (int i = 0; i < expectedLessList.size(); i++) {
            expectedLessThanItems[i] = expectedLessList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedLessThanItems, av.lessThanCriteria());
    }

    @Test
    public void betweenCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedBetweenList = session.createQuery("From Item where itemPrice between 100 and 200").list();
        final String expectedPriceBetweenItems[] = new String[expectedBetweenList.size()];
        for (int i = 0; i < expectedBetweenList.size(); i++) {
            expectedPriceBetweenItems[i] = expectedBetweenList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedPriceBetweenItems, av.betweenCriteria());
    }

    @Test
    public void inCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedInList = session.createQuery("From Item where itemName in ('Skate Board', 'Paint', 'Glue')").list();
        final String expectedNameInItems[] = new String[expectedInList.size()];
        for (int i = 0; i < expectedInList.size(); i++) {
            expectedNameInItems[i] = expectedInList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedNameInItems, av.inCriteria());
    }

    @Test
    public void givenNewItemPrice_whenCriteriaUpdate_thenReturnAffectedResult() {

        int oldPrice = 10, newPrice = 20;

        Session session = HibernateUtil.getHibernateSession();

        Item item = new Item(12, "Test Item 12", "This is a description");
        item.setItemPrice(oldPrice);
        session.save(item);

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaUpdate<Item> criteriaUpdate = cb.createCriteriaUpdate(Item.class);
        Root<Item> root = criteriaUpdate.from(Item.class);
        criteriaUpdate.set("itemPrice", newPrice);
        criteriaUpdate.where(cb.equal(root.get("itemPrice"), oldPrice));

        Transaction transaction = session.beginTransaction();
        session.createQuery(criteriaUpdate).executeUpdate();
        transaction.commit();

        Item updatedItem = session.createQuery("FROM Item WHERE itemPrice = " + newPrice, Item.class).getSingleResult();
        session.refresh(updatedItem);
        assertEquals(newPrice, updatedItem.getItemPrice().intValue());
    }

    @Test
    public void givenTargetItemPrice_whenCriteriaDelete_thenDeleteMatched() {

        int targetPrice = 1000;

        Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Item> criteriaDelete = cb.createCriteriaDelete(Item.class);
        Root<Item> root = criteriaDelete.from(Item.class);
        criteriaDelete.where(cb.greaterThan(root.get("itemPrice"), targetPrice));

        Transaction transaction = session.beginTransaction();
        session.createQuery(criteriaDelete).executeUpdate();
        transaction.commit();

        List<Item> deletedItem = session.createQuery("FROM Item WHERE itemPrice > " + targetPrice, Item.class).list();
        assertTrue(deletedItem.isEmpty());

    }

}
