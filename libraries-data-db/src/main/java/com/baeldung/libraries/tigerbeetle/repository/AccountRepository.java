package com.baeldung.libraries.tigerbeetle.repository;

import com.baeldung.libraries.tigerbeetle.domain.Account;
import com.tigerbeetle.*;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountRepository {
    private final Client client;

    public Account createAccount(UUID id, BigInteger accountHolderId, short code, int ledger) throws ConcurrencyExceededException  {

        AccountBatch batch = new AccountBatch(1);
        batch.add();
        batch.setId(UInt128.asBytes(id));
        batch.setUserData128(UInt128.asBytes(accountHolderId));
        batch.setLedger(ledger);
        batch.setCode(code);
        batch.setFlags(AccountFlags.HISTORY);

        CreateAccountResultBatch result = client.createAccounts(batch);
        if(result.getLength() > 0) {
            result.next();
            throw new IllegalStateException("Error creating account: result=" + result.getResult().name());
        }

        return findAccountById(id);


    }

    public  Account findAccountById(UUID id) throws ConcurrencyExceededException {
        IdBatch idBatch = new IdBatch(UInt128.asBytes(id));
        var batch = client.lookupAccounts(idBatch);
        batch.next();

        return Account.builder()
          .id(UInt128.asUUID(batch.getId()))
          .accountHolderId(UInt128.asBigInteger(batch.getUserData128()))
          .flags(batch.getFlags())
          .code(batch.getCode())
          .ledger(batch.getLedger())
          .timestamp(batch.getTimestamp())
          .creditsPosted(batch.getCreditsPosted())
          .creditsPending(batch.getCreditsPending())
          .debtsPending(batch.getCreditsPending())
          .debtsPosted(batch.getDebitsPosted())
          .build();
    }

//    public UUID createSimpleTransfer(UUID sourceAccount, UUID targetAccount, BigInteger amount, int ledger, int code ) {
//
//    }

}
