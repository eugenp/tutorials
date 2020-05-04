package com.baeldung.hexagonal.banking.application.service;

import com.baeldung.hexagonal.banking.input.port.OpenAccontUseCasePort;
import com.baeldung.hexagonal.banking.input.port.OpenCommercialAccountCommand;
import com.baeldung.hexagonal.banking.input.port.OpenConsumerAccountCommand;

public class OpenAccountService implements OpenAccontUseCasePort {

    @Override
    public Long openCommercialAccount(OpenCommercialAccountCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long openConsumerAccount(OpenConsumerAccountCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

}
