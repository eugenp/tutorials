package com.baeldung.libraries.tigerbeetle;

import com.baeldung.libraries.tigerbeetle.config.TigerBeetleConfig;
import com.baeldung.libraries.tigerbeetle.domain.Transfer;
import com.baeldung.libraries.tigerbeetle.repository.AccountRepository;
import com.tigerbeetle.AccountFlags;
import com.tigerbeetle.CreateTransferResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { TigerBeetleConfig.class, AccountRepository.class})
public class TigerBeetleLiveTest {

    @Autowired
    private AccountRepository repo;

    @Test
    void whenCreateAccount_thenSuccess() throws Exception {

        var id = UUID.randomUUID();

        var acc = repo.createAccount(
          BigInteger.valueOf(99999999L),
          1000,
          1,0,0,0);

        assertNotNull(acc);
        assertEquals(BigInteger.ZERO, acc.creditsPending());
        assertEquals(BigInteger.ZERO, acc.creditsPosted());
        assertEquals(BigInteger.ZERO, acc.debtsPending());
        assertEquals(BigInteger.ZERO, acc.debtsPosted());
    }

    @Test
    void whenSimpleTransfer_thenSuccess() throws Exception {

        var MY_LEDGER = 1000;
        var CHECKING_ACCOUNT = 1000;
        var P2P_TRANSFER = 500;

        var liabilitiesAcc = repo.createAccount(
          BigInteger.valueOf(1000L),
          CHECKING_ACCOUNT,
          MY_LEDGER, 0,0, 0);

        var sourceAcc = repo.createAccount(
          BigInteger.valueOf(1001L),
          CHECKING_ACCOUNT,
          MY_LEDGER, 0,0, AccountFlags.DEBITS_MUST_NOT_EXCEED_CREDITS);

        var targetAcc = repo.createAccount(
          BigInteger.valueOf(1002L),
          CHECKING_ACCOUNT,
          MY_LEDGER, 0, 0, 0);

        List<Transfer> transfers = List.of(
          Transfer.builder()
            .debitAccountId(liabilitiesAcc.id())
            .ledger(MY_LEDGER)
            .code(P2P_TRANSFER)
            .creditAccountId(sourceAcc.id())
            .amount(BigInteger.valueOf(1_000L))
            .build(),
          Transfer.builder()
            .debitAccountId(sourceAcc.id())
            .ledger(MY_LEDGER)
            .code(P2P_TRANSFER)
            .creditAccountId(targetAcc.id())
            .amount(BigInteger.valueOf(2_000L))
            .build()
          );

        var results = repo.createLinkedTransfers(transfers);
        assertEquals(2, results.size());
        assertEquals(CreateTransferResult.LinkedEventFailed, results.get(0).getValue());
        assertEquals(CreateTransferResult.ExceedsCredits, results.get(1).getValue());

    }

}
