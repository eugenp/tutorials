package com.baeldung.hexagonal.banking.input.port;

public interface OpenAccontUseCasePort {

    public Long openAccount(OpenAccountCommand command);

}
