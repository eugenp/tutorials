package com.baeldung.hexExample.adapters.persistance;

import com.baeldung.hexExample.application.domain.BankAccount;
import com.baeldung.hexExample.application.port.outgoing.LoadAccountPort;
import com.baeldung.hexExample.application.port.outgoing.SaveAccountPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BankAccountRepository implements LoadAccountPort, SaveAccountPort {

    private SpringDataBankAccountRepository repository;

    public BankAccountRepository(SpringDataBankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BankAccount> load(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(BankAccount bankAccount) {
        repository.save(bankAccount);
    }
}
