package com.baeldung.domain.port.outgoing;

import com.baeldung.domain.model.Account;

public interface RetrieveAccount {

    Account load(Long accountNo);
}