package com.baeldung.hexagonal;

// This is the client-facing application, maybe UI or microservice controller
public class BankApplication {

    private IBankInputPort inputPort;
    public BankApplication(IBankInputPort inputPort) {
        this.inputPort = inputPort;
    }

    public void addAmount(String accountNumber, Double amount) {
        inputPort.credit(accountNumber, amount);
    }

    public void withdrawAmount(String accountNumber, Double amount) {
        inputPort.debit(accountNumber, amount);
    }
}
