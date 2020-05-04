package com.baeldung.hexagonal.banking.input.port;

public interface OpenAccontUseCasePort {

    public Long openCommercialAccount(OpenCommercialAccountCommand command);

    public Long openConsumerAccount(OpenConsumerAccountCommand command);


}
