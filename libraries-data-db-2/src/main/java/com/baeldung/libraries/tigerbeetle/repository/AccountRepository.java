package com.baeldung.libraries.tigerbeetle.repository;

import com.baeldung.libraries.tigerbeetle.domain.*;
import com.tigerbeetle.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountRepository {
    private final Client client;

    public Account createAccount(BigInteger accountHolderId, int code, int ledger, int userData32, long userData64, int flags ) throws ConcurrencyExceededException  {

        AccountBatch batch = new AccountBatch(1);
        byte[] id = UInt128.id();
        batch.add();
        batch.setId(id);
        batch.setUserData128(UInt128.asBytes(accountHolderId));
        batch.setLedger(ledger);
        batch.setCode(code);
        batch.setUserData32(userData32);
        batch.setUserData64(userData64);
        batch.setFlags(AccountFlags.HISTORY | flags);

        CreateAccountResultBatch result = client.createAccounts(batch);
        if(result.getLength() > 0) {
            result.next();
            throw new AccountException(result.getResult());
        }

        return findAccountById(UInt128.asUUID(id)).orElseThrow();
    }

    public  Optional<Account> findAccountById(UUID id) throws ConcurrencyExceededException {
        IdBatch idBatch = new IdBatch(UInt128.asBytes(id));
        var batch = client.lookupAccounts(idBatch);

        if ( !batch.next() ) {
            return Optional.empty();
        }

        return Optional.of(mapFromCurrentAccountBatch(batch));
    }

    public Map<UUID,Account> findAccountsById(UUID[] ids) throws ConcurrencyExceededException {

        IdBatch idBatch = new IdBatch(ids.length);
        Map<UUID,Account> result = new HashMap<>();
        for ( UUID id : ids) {
            idBatch.add(UInt128.asBytes(id));
            result.put(id,null);
        }

        var batch = client.lookupAccounts(idBatch);
        while(batch.next()) {
            UUID key = UInt128.asUUID(batch.getId());
            Account acc = mapFromCurrentAccountBatch(batch);
            result.put(key,acc);
        }

        return result;

    }

    private Account mapFromCurrentAccountBatch(AccountBatch batch) {
        return Account.builder()
          .id(UInt128.asUUID(batch.getId()))
          .accountHolderId(UInt128.asBigInteger(batch.getUserData128()))
          .flags(batch.getFlags())
          .code(batch.getCode())
          .ledger(batch.getLedger())
          .userData32(batch.getUserData32())
          .userData64(batch.getUserData64())
          .timestamp(batch.getTimestamp())
          .creditsPosted(batch.getCreditsPosted())
          .creditsPending(batch.getCreditsPending())
          .debtsPending(batch.getCreditsPending())
          .debtsPosted(batch.getDebitsPosted())
          .build();
    }

    public UUID createSimpleTransfer(UUID sourceAccount, UUID targetAccount, BigInteger amount, int ledger, int code, UUID userData128, long userData64, int userData32 ) throws ConcurrencyExceededException {

        var id = UInt128.id();
        var batch = new TransferBatch(1);

        batch.add();
        batch.setId(id);
        batch.setAmount(amount);
        batch.setCode(code);
        batch.setCreditAccountId(UInt128.asBytes(targetAccount));
        batch.setDebitAccountId(UInt128.asBytes(sourceAccount));
        batch.setUserData32(userData32);
        batch.setUserData64(userData64);
        batch.setUserData128(UInt128.asBytes(userData128));
        batch.setLedger(ledger);

        var batchResults = client.createTransfers(batch);

        if ( batchResults.getLength() > 0) {
            batchResults.next();
            throw new TransferException(batchResults.getResult());
        }
        return UInt128.asUUID(id);
    }

    public UUID createPendingTransfer(UUID sourceAccount, UUID targetAccount, BigInteger amount, int ledger, int code, UUID userData128, long userData64, int userData32 ) throws ConcurrencyExceededException {

        var id = UInt128.id();
        var batch = new TransferBatch(1);

        batch.add();
        batch.setId(id);
        batch.setAmount(amount);
        batch.setCode(code);
        batch.setCreditAccountId(UInt128.asBytes(targetAccount));
        batch.setDebitAccountId(UInt128.asBytes(sourceAccount));
        batch.setFlags(TransferFlags.PENDING);
        batch.setUserData32(userData32);
        batch.setUserData64(userData64);
        batch.setUserData128(UInt128.asBytes(userData128));
        batch.setLedger(ledger);

        var batchResults = client.createTransfers(batch);

        if ( batchResults.getLength() > 0) {
            batchResults.next();
            throw new TransferException(batchResults.getResult());
        }
        return UInt128.asUUID(id);
    }

    public UUID createExpirablePendingTransfer(UUID sourceAccount, UUID targetAccount, BigInteger amount, int ledger, int code, UUID userData128, long userData64, int userData32, int timeout ) throws ConcurrencyExceededException {

        var id = UInt128.id();
        var batch = new TransferBatch(1);

        batch.add();
        batch.setId(id);
        batch.setAmount(amount);
        batch.setCode(code);
        batch.setCreditAccountId(UInt128.asBytes(targetAccount));
        batch.setDebitAccountId(UInt128.asBytes(sourceAccount));
        batch.setFlags(TransferFlags.PENDING);
        batch.setUserData32(userData32);
        batch.setUserData64(userData64);
        if ( userData128 != null ) {
            batch.setUserData128(UInt128.asBytes(userData128));
        }
        batch.setLedger(ledger);
        batch.setTimeout(timeout);

        var batchResults = client.createTransfers(batch);

        if ( batchResults.getLength() > 0) {
            batchResults.next();
            throw new TransferException(batchResults.getResult());
        }
        return UInt128.asUUID(id);
    }

    public UUID completePendingTransfer(UUID pendingId, boolean success) throws ConcurrencyExceededException {

        var id = UInt128.asUUID(UInt128.id());
        var batch = new TransferBatch(1);

        batch.add();
        batch.setPendingId(UInt128.asBytes(pendingId));
        batch.setFlags(success? TransferFlags.POST_PENDING_TRANSFER : TransferFlags.VOID_PENDING_TRANSFER);

        var batchResults = client.createTransfers(batch);

        if ( batchResults.getLength() > 0) {
            batchResults.next();
            throw new TransferException(batchResults.getResult());
        }
        return id;
    }

    public List<Transfer> listAccountTransfers(UUID accountId, Instant start, Instant end, int limit, boolean lastFirst) throws ConcurrencyExceededException {

        var filter = new AccountFilter();
        filter.setAccountId(UInt128.asBytes(accountId));
        filter.setCredits(true);
        filter.setDebits(true);
        filter.setReversed(lastFirst);
        filter.setTimestampMin(start.toEpochMilli());
        filter.setTimestampMax(end.toEpochMilli());
        filter.setLimit(limit);

        var batch = client.getAccountTransfers(filter);
        var result = new ArrayList<Transfer>();
        while(batch.next()) {
            Transfer tr = Transfer.builder()
              .id(UInt128.asUUID(batch.getId()))
              .code(batch.getCode())
              .amount(batch.getAmount())
              .flags(batch.getFlags())
              .ledger(batch.getLedger())
              .creditAccountId(UInt128.asUUID(batch.getCreditAccountId()))
              .debitAccountId(UInt128.asUUID(batch.getDebitAccountId()))
              .userData128(UInt128.asUUID(batch.getUserData128()))
              .userData64(batch.getUserData64())
              .userData32(batch.getUserData32())
              .timestamp(Instant.ofEpochMilli(batch.getTimestamp()))
              .pendingId(UInt128.asUUID(batch.getPendingId()))
              .build();

            result.add(tr);
        }

        return result;
    }

    public List<Balance> listAccountBalances(UUID accountId, Instant start, Instant end, int limit, boolean lastFirst) throws  ConcurrencyExceededException {
        var filter = new AccountFilter();
        filter.setAccountId(UInt128.asBytes(accountId));
        filter.setCredits(true);
        filter.setDebits(true);
        filter.setLimit(limit);
        filter.setReversed(lastFirst);
        filter.setTimestampMin(start.toEpochMilli());
        filter.setTimestampMax(end.toEpochMilli());

        var batch = client.getAccountBalances(filter);
        var result = new ArrayList<Balance>();
        while(batch.next()) {
            result.add(
              Balance.builder()
                .accountId(accountId)
                .debitsPending(batch.getDebitsPending())
                .debitsPosted(batch.getDebitsPosted())
                .creditsPending(batch.getCreditsPending())
                .creditsPosted(batch.getCreditsPosted())
                .timestamp(Instant.ofEpochMilli(batch.getTimestamp()))
                .build()
            );
        }

        return result;
    }

    public List<Map.Entry<UUID,CreateTransferResult>> createLinkedTransfers(List<Transfer> transfers) throws ConcurrencyExceededException {

        var results = new ArrayList<Map.Entry<UUID,CreateTransferResult>>(transfers.size());
        var batch = new TransferBatch(transfers.size());
        for ( Transfer t : transfers) {
            byte[] id = UInt128.id();
            batch.add();
            batch.setId(id);

            // Is this the last transfer to add ?
            if ( batch.getPosition() != transfers.size() -1 ) {
                batch.setFlags(TransferFlags.LINKED);
            }

            batch.setLedger(t.ledger());
            batch.setAmount(t.amount());
            batch.setDebitAccountId(UInt128.asBytes(t.debitAccountId()));
            batch.setCreditAccountId(UInt128.asBytes(t.creditAccountId()));
            if ( t.userData128() != null) {
                batch.setUserData128(UInt128.asBytes(t.userData128()));
            }
            batch.setCode(t.code());
            results.add(new AbstractMap.SimpleImmutableEntry<>(UInt128.asUUID(id), CreateTransferResult.Ok));
        }

        var batchResult = client.createTransfers(batch);
        while(batchResult.next()) {
            var original = results.get(batchResult.getIndex());
            results.set(batchResult.getIndex(), new AbstractMap.SimpleImmutableEntry<>(original.getKey(), batchResult.getResult()));
        }

        return results;
    }

}
