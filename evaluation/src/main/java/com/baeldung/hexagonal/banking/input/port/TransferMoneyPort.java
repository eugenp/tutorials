package com.baeldung.hexagonal.banking.input.port;

import com.baeldung.hexagonal.banking.application.service.InvalidPinException;
import com.baeldung.hexagonal.banking.application.service.NotEnoughBalanceException;

public interface TransferMoneyPort {
    
    void transferMoney(TransferMoneyCommand command) throws InvalidPinException, NotEnoughBalanceException;

}
