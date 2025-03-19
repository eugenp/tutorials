package com.baeldung.hibernate.criteria;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class HqlToCriteriaIntegrationTest {
    private Session session;

    @Before
    public void setUp() {
        session = HibernateUtil.getHibernateSession();
    }

    @Test
    public void testCreateCriteriaFromHql() {
        // Step 1: Get the CriteriaBuilder from the session
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Step 2: Create a CriteriaQuery using HQL (as a string)
        // HQL query: "select itemId, itemName from Item order by itemPrice"
        CriteriaQuery<Object[]> hqlCriteria = session.getCriteriaBuilder()
                .createQuery("select itemId, itemName from Item order by itemPrice", Object[].class);

        // Step 3: Mutate the CriteriaQuery
        // Let's add a WHERE clause to filter items with itemPrice > 100
        Root<Item> root = hqlCriteria.from(Item.class);
        hqlCriteria.where(builder.greaterThan(root.get("itemPrice"), 100));

        // Step 4: Execute the query and get the result list
        List<Object[]> resultList = session.createQuery(hqlCriteria).getResultList();

        // Step 5: Simple assertion to check if the result is not empty
        assertTrue("The result list should contain items", resultList.size() > 0);

        // Print results for demonstration purposes
        for (Object[] result : resultList) {
            Integer itemId = (Integer) result[0];  // itemId at index 0
            String itemName = (String) result[1];  // itemName at index 1
            System.out.println("Item ID: " + itemId + ", Item Name: " + itemName);
        }
    }
}
