package com.baeldung.hexagonal.domain;

public class Customer {

    private final String id;
    private final String name;

    private final NotificationService notificationService;

    public Customer(String id, String name, NotificationService notificationService) {
        this.id = id;
        this.name = name;
        this.notificationService = notificationService;
    }

    public Customer changeName(String otherName) {
        Customer result = new Customer(id, otherName, notificationService);
        notificationService.notifyCustomerChanged(result);
        return result;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
