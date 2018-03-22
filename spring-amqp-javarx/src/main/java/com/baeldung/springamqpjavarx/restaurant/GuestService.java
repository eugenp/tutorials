package com.baeldung.springamqpjavarx.restaurant;

import rx.subjects.PublishSubject;

public class GuestService {

    private final PublishSubject<Order> orderPool;

    GuestService() {
        final Waiter waiter = new Waiter(new Kitchen());

        orderPool = PublishSubject.create();
        orderPool.subscribe(waiter);
    }

    public void receiveOrder(String orderCode) {
        try {
            Order order = Order.getByCode(orderCode);
            orderPool.onNext(order);
        } catch (Order.NoSuchOrderException e) {
            orderPool.onCompleted();
        }

    }
}
