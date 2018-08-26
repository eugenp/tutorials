package com.baeldung.jtademo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class TellerService {
    private final BankAccountService bankAccountService;
    private final AuditService auditService;

    @Autowired
    public TellerService(BankAccountService bankAccountService, AuditService auditService) {
        this.bankAccountService = bankAccountService;
        this.auditService = auditService;
    }

    @Transactional
    public void executeTransfer(String fromAccontId, String toAccountId, BigDecimal amount) {
        bankAccountService.transfer(fromAccontId, toAccountId, amount);
        auditService.log(fromAccontId, toAccountId, amount);
    }

    @Transactional
    public void executeTransferFail(String fromAccontId, String toAccountId, BigDecimal amount) {
        bankAccountService.transfer(fromAccontId, toAccountId, amount);
        auditService.log(fromAccontId, toAccountId, amount);
        throw new RuntimeException("Something wrong, rollback!");
    }
}
