package com.baeldung.demeter;

public class Expenses {

    private double total;
    private double tax;

    public Expenses(double total, double tax) {
        this.total = total;
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public double getTax() {
        return tax;
    }

    public double calculateTotalWithTax() {
        return total + tax;
    }
}
