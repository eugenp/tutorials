package com.baeldung.hexagonal.domain;

public class Customer {

    private final String id;
    private final String name;

    private final MessagingService messagingService;

    public Customer(String id, String name, MessagingService messagingService) {
        this.id = id;
        this.name = name;
        this.messagingService = messagingService;
    }

    public Customer changeName(String otherName) {
        Customer result = new Customer(id, otherName, messagingService);
        messagingService.publishMessage(result);
        return result;
    }

}
