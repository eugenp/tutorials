package com.baeldung.hibernate.userdefinedfunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Item;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class UserDefinedFunctionIntegrationTest {
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        // Create the calculate_discount function in the database
        session.createNativeQuery(
            "CREATE ALIAS calculate_discount AS $$" +
                "double calculateDiscount(double itemPrice) {" +
                "double discountRate = 0.1;" +
                "return itemPrice - (itemPrice * discountRate);" +
                "}" +
                "$$"
        ).executeUpdate();

        // Create and persist test data
        Item item1 = new Item("Laptop", 1500.00);
        Item item2 = new Item("Smartphone", 800.00);
        Item item3 = new Item("Tablet", 500.00);
        Item item4 = new Item("Headphones", 200.00);
        Item item5 = new Item("Smartwatch", 350.00);

        session.persist(item1);
        session.persist(item2);
        session.persist(item3);
        session.persist(item4);
        session.persist(item5);

        // Commit the transaction
        transaction.commit();
    }

    @AfterEach
    public void tearDown() {
        if (transaction != null) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void givenItemPrice_whenCalculateDiscount_thenReturnsDiscountedItems() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> root = query.from(Item.class);
        Expression<Double> discountPrice = cb.function("calculate_discount", Double.class, root.get("itemPrice"));
        query.select(root).where(cb.lt(discountPrice, 500.0));
        List<Item> items = session.createQuery(query).getResultList();

        assertNotNull(items, "Discounted items should not be null");
        assertFalse(items.isEmpty(), "There should be discounted items returned");
        assertEquals(3, items.size());
    }
}
