package com.baeldung.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class TestOrderService {

    @Autowired
    private TestOrderRepository testOrderRepository;

    @Autowired
    @Lazy
    private TestOrderService self;

    @Transactional
    public void createOrderPublic(TestOrder order) {
        testOrderRepository.save(order);
        throw new RuntimeException("Rollback test");
    }

    @Transactional
    private void createOrderPrivate(TestOrder order) {
        testOrderRepository.save(order);
        //throw new RuntimeException("Rollback test");
    }

    @Transactional
    void createOrderPackagePrivate(TestOrder order) {
        testOrderRepository.save(order);
        throw new RuntimeException("Rollback test");
    }

    @Transactional
    protected void createOrderProtected(TestOrder order) {
        testOrderRepository.save(order);
        throw new RuntimeException("Rollback test");
    }

    public void callPrivateMethod(TestOrder order) {
        self.createOrderPrivate(order);
    }
}
