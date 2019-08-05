package com.baeldung.patterns.hexagonal.transfer;

import com.baeldung.patterns.hexagonal.domain.Account;
import com.baeldung.patterns.hexagonal.out.FindAccountByIdPort;
import com.baeldung.patterns.hexagonal.out.PersistAccountPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class TransferFundsService implements TransferFundsUseCase {

    private FindAccountByIdPort findAccountByIdPort;
    private PersistAccountPort persistAccountPort;

    @Override
    public void transferFunds(TransferFundsCommand command) {
        Account from = findAccountByIdPort.findById(command.getFrom());
        Account to = findAccountByIdPort.findById(command.getTo());
        from.setFundsLeft(from.getFundsLeft() - command.getAmount());
        to.setFundsLeft(to.getFundsLeft() + command.getAmount());

        persistAccountPort.save(from);
        persistAccountPort.save(to);
    }
}
