package com.baeldung.jpa.removal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrphanRemovalIntegrationTest {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-h2-removal");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void whenLineItemIsRemovedFromOrderRequest_thenDeleteOrphanedLineItem() {
        createOrderRequestWithLineItems();

        OrderRequest orderRequest = entityManager.find(OrderRequest.class, 1L);
        LineItem lineItem = entityManager.find(LineItem.class, 2L);
        orderRequest.removeLineItem(lineItem);

        entityManager.getTransaction().begin();
        entityManager.merge(orderRequest);
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, findAllOrderRequest().size());
        Assert.assertEquals(2, findAllLineItem().size());
    }

    private void createOrderRequestWithLineItems() {
        List<LineItem> lineItems = new ArrayList<>();
        lineItems.add(new LineItem("line item 1"));
        lineItems.add(new LineItem("line item 2"));
        lineItems.add(new LineItem("line item 3"));

        OrderRequest orderRequest = new OrderRequest(lineItems);

        entityManager.getTransaction().begin();
        entityManager.persist(orderRequest);
        entityManager.getTransaction().commit();

        Assert.assertEquals(1, findAllOrderRequest().size());
        Assert.assertEquals(3, findAllLineItem().size());
    }

    private List<OrderRequest> findAllOrderRequest() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderRequest> cq = cb.createQuery(OrderRequest.class);
        Root<OrderRequest> root = cq.from(OrderRequest.class);
        CriteriaQuery<OrderRequest> findAll = cq.select(root);
        TypedQuery<OrderRequest> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }

    private List<LineItem> findAllLineItem() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LineItem> cq = cb.createQuery(LineItem.class);
        Root<LineItem> root = cq.from(LineItem.class);
        CriteriaQuery<LineItem> findAll = cq.select(root);
        TypedQuery<LineItem> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }
}
