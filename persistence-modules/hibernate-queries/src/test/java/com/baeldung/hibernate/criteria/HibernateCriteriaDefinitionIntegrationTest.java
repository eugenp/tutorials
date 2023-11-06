package com.baeldung.hibernate.criteria;

import static org.junit.Assert.assertArrayEquals;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;
import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;
import com.baeldung.hibernate.criteria.view.CriteriaDefinitionApplicationView;

public class HibernateCriteriaDefinitionIntegrationTest {

    final private CriteriaDefinitionApplicationView criteriaDefinition = new CriteriaDefinitionApplicationView();

    @Test
    public void givenHibernateSession_whenExecutingLikeCriteriaQuery_thenExpectMatchingItems() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedLikeList = session.createQuery("From Item where itemName like '%chair%'").list();
        final String expectedLikeItems[] = new String[expectedLikeList.size()];
        for (int i = 0; i < expectedLikeList.size(); i++) {
            expectedLikeItems[i] = expectedLikeList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedLikeItems, criteriaDefinition.likeCriteria());
    }

    @Test
    public void givenHibernateSession_whenExecutingCaseSensitiveLikeCriteriaQuery_thenExpectMatchingItems() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedChairCaseList = session.createQuery("From Item where itemName like '%Chair%'").list();
        final String expectedChairCaseItems[] = new String[expectedChairCaseList.size()];
        for (int i = 0; i < expectedChairCaseList.size(); i++) {
            expectedChairCaseItems[i] = expectedChairCaseList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedChairCaseItems, criteriaDefinition.likeCaseCriteria());
    }

    @Test
    public void givenHibernateSession_whenExecutingGreaterThanCriteriaQuery_thenExpectMatchingItems() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedGreaterThanList = session.createQuery("From Item where itemPrice>1000").list();
        final String expectedGreaterThanItems[] = new String[expectedGreaterThanList.size()];
        for (int i = 0; i < expectedGreaterThanList.size(); i++) {
            expectedGreaterThanItems[i] = expectedGreaterThanList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedGreaterThanItems, criteriaDefinition.greaterThanCriteria());
    }

    @Test
    public void givenHibernateSession_whenExecutingLessThanCriteriaQuery_thenExpectMatchingItems() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedLessList = session.createQuery("From Item where itemPrice<1000").list();
        final String expectedLessThanItems[] = new String[expectedLessList.size()];
        for (int i = 0; i < expectedLessList.size(); i++) {
            expectedLessThanItems[i] = expectedLessList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedLessThanItems, criteriaDefinition.lessThanCriteria());
    }

    @Test
    public void givenHibernateSession_whenExecutingBetweenCriteriaQuery_thenExpectMatchingItems() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Item> expectedBetweenList = session.createQuery("From Item where itemPrice between 100 and 200").list();
        final String expectedPriceBetweenItems[] = new String[expectedBetweenList.size()];
        for (int i = 0; i < expectedBetweenList.size(); i++) {
            expectedPriceBetweenItems[i] = expectedBetweenList.get(i).getItemName();
        }
        session.close();
        assertArrayEquals(expectedPriceBetweenItems, criteriaDefinition.betweenCriteria());
    }


}
