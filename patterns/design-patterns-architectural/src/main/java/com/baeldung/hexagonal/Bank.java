package com.baeldung.hexagonal;

public class Bank {
    public static void main(String[] args) {
        IBankOutputPort outputPort = new BankOutputAdapter();

        IBankInputPort inputPort = new BankInputAdapter(outputPort);

        BankApplication application = new BankApplication(inputPort);

        application.addAmount("acc", 10.0);
        application.withdrawAmount("acc", 5.0);
    }
}
