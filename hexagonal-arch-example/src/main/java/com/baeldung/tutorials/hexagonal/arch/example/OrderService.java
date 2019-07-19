package com.baeldung.tutorials.hexagonal.arch.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
 
    private static int MAJOR_UNIT_CUTOFF = 1000;
 
    @Autowired
    private OrderDao dao;
 
    public void create(int unitCount) {
 
        dao.save(unitCount);
    }
 
    public boolean isMajor(int orderId) {
 
        Order order = getById(orderId);
 
        return order.getUnitCount() > MAJOR_UNIT_CUTOFF;
    }
 
    public Order getById(int id) {
 
        return dao.get(id);
    }
}