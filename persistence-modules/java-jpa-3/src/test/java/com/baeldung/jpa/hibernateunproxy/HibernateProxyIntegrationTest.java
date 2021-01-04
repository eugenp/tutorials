package com.baeldung.jpa.hibernateunproxy;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HibernateProxyIntegrationTest {

    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-hibernate-unproxy");
        entityManager = factory.createEntityManager();
        populateH2DB();
    }

    @Test
    public void givenPaymentReceipt_whenAccessingPayment_thenVerifyType() {
        PaymentReceipt paymentReceipt = entityManager.find(PaymentReceipt.class, 3L);
        Assert.assertEquals(true, paymentReceipt.getPayment() instanceof HibernateProxy);
    }

    @Test
    public void givenWebUserProxy_whenCreatingPayment_thenExecuteSingleStatement() {
        entityManager.getTransaction().begin();

        WebUser webUser = entityManager.getReference(WebUser.class, 1L);
        Payment payment = new CreditCardPayment(new BigDecimal(100), webUser, "CN-1234");
        entityManager.persist(payment);

        entityManager.getTransaction().commit();
        Assert.assertEquals(true, webUser instanceof HibernateProxy);
    }

    @Test
    public void givenPaymentReceipt_whenCastingPaymentToConcreteClass_thenThrowClassCastException() {
        PaymentReceipt paymentReceipt = entityManager.find(PaymentReceipt.class, 3L);
        assertThrows(ClassCastException.class, () -> {
            CreditCardPayment creditCardPayment = (CreditCardPayment) paymentReceipt.getPayment();
        });
    }

    @Test
    public void givenPaymentReceipt_whenPaymentIsUnproxied_thenReturnRealEntityObject() {
        PaymentReceipt paymentReceipt = entityManager.find(PaymentReceipt.class, 3L);
        Assert.assertEquals(true, Hibernate.unproxy(paymentReceipt.getPayment()) instanceof CreditCardPayment);
    }

    private static void populateH2DB() {
        entityManager.getTransaction().begin();

        WebUser webUser = new WebUser("name");
        entityManager.persist(webUser);

        Payment payment = new CreditCardPayment(new BigDecimal(100), webUser, "CN-1234");
        entityManager.persist(payment);

        PaymentReceipt receipt = new PaymentReceipt(payment);
        entityManager.persist(receipt);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

}
