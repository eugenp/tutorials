package com.baeldung.tutorials.hexagonal.arch.example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class OrderDaoImpl implements OrderDao {
 
    @PersistenceContext
    private EntityManager em;
 
    @Transactional
    public void save(int unitCount) {
 
        Order order = new Order();
 
        order.setUnitCount(unitCount);
        em.persist(order);
    }
 
    public Order get(int id) {
 
        return em.find(Order.class, id);
    }
}

