package com.baeldung.hexagonal.banking.input.port;

public interface TransferMoneyUseCasePort {
    
    boolean transferMoney(TransferMoneyCommand command);

}
