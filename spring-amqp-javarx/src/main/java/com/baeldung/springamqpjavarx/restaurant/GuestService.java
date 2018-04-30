package com.baeldung.springamqpjavarx.restaurant;

import rx.BackpressureOverflow;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class GuestService {

    private final PublishSubject<Order> orderPool;

    GuestService() {
        final Waiter waiter = new Waiter(new Kitchen());

        orderPool = PublishSubject.create();

        orderPool
          .doOnNext(obj -> System.out.println("Order received!"))
          .observeOn(Schedulers.computation())
          .onBackpressureBuffer(2,
            () -> System.out.println("Buffer overflow"),
            BackpressureOverflow.ON_OVERFLOW_DROP_LATEST)
          .subscribe(waiter);

        //executionLoop.init();
    }

    public void receiveOrder(String orderCode) {
        try {
            Order order = Order.getByCode(orderCode);
            orderPool.onNext(order);
        } catch (Order.NoSuchOrderException e) {
            orderPool.onCompleted();
        }
    }

    public static void main(String[] args) {

        GuestService service = new GuestService();

        new Thread(() -> service.receiveOrder("pizza")).start();
        new Thread(() -> service.receiveOrder("ice_cream")).start();
        new Thread(() -> service.receiveOrder("soda")).start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
