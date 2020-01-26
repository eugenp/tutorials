package com.baeldung.hexagonal;

public class Bank {
        public static void main(String[] args) {
                IBankOutputPort outputPort = new BankOutputAdapter();

                BankDomainService domainService = new BankDomainService(outputPort);

                IBankInputPort inputPort = new BankInputAdapter(domainService);

                BankApplication application = new BankApplication(inputPort);

                application.addAmount("acc", 10.0);
                application.withdrawAmount("acc", 5.0);
        }
}
