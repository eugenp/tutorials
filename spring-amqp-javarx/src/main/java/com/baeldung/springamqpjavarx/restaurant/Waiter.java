package com.baeldung.springamqpjavarx.restaurant;

import rx.Observer;

public class Waiter implements Observer<Order> {

    private final Kitchen kitchen;

    Waiter(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    @Override
    public void onNext(Order order) {
        System.out.printf("Order received: [%s]\n", order);
        Kitchen.ChefGreeting greeting = kitchen.processOrder(order);
        System.out.println(greeting.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("It's been a long day");
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
