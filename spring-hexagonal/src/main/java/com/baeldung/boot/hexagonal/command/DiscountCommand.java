package com.baeldung.boot.hexagonal.command;

public class DiscountCommand {
    private final double amount;

    public DiscountCommand(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
