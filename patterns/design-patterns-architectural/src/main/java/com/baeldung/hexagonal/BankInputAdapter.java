package com.baeldung.hexagonal;

public class BankInputAdapter implements IBankInputPort {

    private IBankOutputPort outputPort;

    public BankInputAdapter(IBankOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    @Override
    public void credit(String accountNumber, Double amount) {
        Double balance = outputPort.getBalance(accountNumber);
        double newBalance = balance + amount;
        if(newBalance > 0) {
            outputPort.updateBalance(accountNumber, newBalance);
        }
    }

    @Override
    public void debit(String accountNumber, Double amount) {
        Double balance = outputPort.getBalance(accountNumber);
        double newBalance = balance - amount;
        if(newBalance > 0) {
            outputPort.updateBalance(accountNumber, newBalance);
        }
    }
}
