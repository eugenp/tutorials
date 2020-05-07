package com.baeldung.hexagonal.banking.input.adapter;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.banking.input.port.TransferMoneyCommand;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyPort;

@RestController
public class TransferMoneyController {
    
    private TransferMoneyPort transferMoneyUseCase;

    public TransferMoneyController(TransferMoneyPort transferMoneyUseCase) {
        super();
        this.transferMoneyUseCase = transferMoneyUseCase;
    }
    
    @PostMapping(path = "/accounts/transfer/{sourceAccountNumber}/{targetAccountNumber}/{amount}/{pin}")
    void transferMoney(
                    @PathVariable("sourceAccountNumber") Long sourceAccountNumber,
                    @PathVariable("targetAccountNumber") Long targetAccountNumber,
                    @PathVariable("amount") Double amount,
                    @PathVariable("pin") int pin) {

            TransferMoneyCommand command = new TransferMoneyCommand(
                            sourceAccountNumber,
                            targetAccountNumber,
                            BigDecimal.valueOf(amount),
                            pin);

            transferMoneyUseCase.transferMoney(command);
    }
    

}
