package com.baeldung.hexagonal_architecture;

public class MyLogic {
    IAmountService service;

    public MyLogic(IAmountService service) {
        this.service = service;
    }

    public int addToAmount(int toAdd, String city) {
        int amount = service.getAmount(city);
        return amount + toAdd;
    }
}
