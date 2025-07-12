package com.baeldung.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private TestOrderRepository repository;

    @Transactional
    public void createOrder(TestOrder order) {
        repository.save(order);
    }

    @Transactional
    public void createOrderPublic(TestOrder order) {
        repository.save(order);
        throw new RuntimeException("Rollback createOrderPublic");
    }

    @Transactional
    void createOrderPackagePrivate(TestOrder order) {
        repository.save(order);
        throw new RuntimeException("Rollback createOrderPackagePrivate");
    }

    @Transactional
    protected void createOrderProtected(TestOrder order) {
        repository.save(order);
        throw new RuntimeException("Rollback createOrderProtected");
    }

    @Transactional
    private void createOrderPrivate(TestOrder order) {
        repository.save(order);
        throw new RuntimeException("Rollback createOrderPrivate");
    }

    public void callPrivate(TestOrder order) {
        createOrderPrivate(order);
    }
}
