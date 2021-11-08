package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.domain.dto.Account;

public interface SignUpServiceInputPort {

    public boolean signup(Account account);
}
