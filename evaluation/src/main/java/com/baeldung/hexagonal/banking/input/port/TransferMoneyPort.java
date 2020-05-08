package com.baeldung.hexagonal.banking.input.port;

public interface TransferMoneyPort {
    
    Boolean transferMoney(TransferMoneyCommand command);

}
