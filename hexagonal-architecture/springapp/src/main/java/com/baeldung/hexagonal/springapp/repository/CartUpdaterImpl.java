package com.baeldung.hexagonal.springapp.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baeldung.hexagonal.springapp.entity.CartEntity;

public class CartUpdaterImpl implements CartUpdater {

    @PersistenceContext EntityManager em;

    @Override public void update(CartEntity cart) {
//        if (cart.getId() != null) {
//            Query statement = em.createQuery("DELETE FROM CartItemEntity item WHERE item.cart.id = :cartId");
//            statement.setParameter("cartId", cart.getId());
//            statement.executeUpdate();
//        }
        em.persist(cart);
    }
}
