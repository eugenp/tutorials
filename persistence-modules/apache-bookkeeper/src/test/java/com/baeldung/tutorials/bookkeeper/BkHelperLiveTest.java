package com.baeldung.tutorials.bookkeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.client.api.LedgerEntries;
import org.apache.bookkeeper.client.api.WriteHandle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BkHelperLiveTest extends BkHelper {
    private static BookKeeper bk;
    private byte[] ledgerPassword = "SuperS3cR37".getBytes();
    private static final Log LOG = LogFactory.getLog(BkHelperLiveTest.class);

    @BeforeAll
    static void initBkClient() {
        bk = createBkClient("192.168.99.101:2181");
    }

    @Test
    void whenCreateLedger_thenSuccess() throws Exception {
        LedgerHandle lh = bk.createLedger(BookKeeper.DigestType.MAC, ledgerPassword);
        assertNotNull(lh);
        assertNotNull(lh.getId());
        LOG.info("[I33] Ledge created: id=" + lh.getId());
    }

    @Test
    void whenCreateLedgerAsync_thenSuccess() throws Exception {

        CompletableFuture<WriteHandle> cf = bk.newCreateLedgerOp()
          .withDigestType(org.apache.bookkeeper.client.api.DigestType.MAC)
          .withPassword("password".getBytes())
          .execute();

        WriteHandle handle = cf.get(1, TimeUnit.MINUTES);
        assertNotNull(handle);
        handle.close();

    }

    @Test
    void whenAsyncCreateLedger_thenSuccess() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<LedgerHandle> handleRef = new AtomicReference<>();

        bk.asyncCreateLedger(3, 2, 2, BookKeeper.DigestType.MAC, ledgerPassword, 
          (rc, lh, ctx) -> {
              handleRef.set(lh);
              latch.countDown();
          }, null, Collections.emptyMap());
        latch.await(1, TimeUnit.MINUTES);
        LedgerHandle lh = handleRef.get();
        assertNotNull(lh);
        assertFalse(lh.isClosed(), "Ledger should be writeable");
    }

    @Test
    void whenListLedgers_thenSuccess() throws Exception {
        List<Long> ledgers = listAllLedgers(bk);
        assertNotNull(ledgers);
    }

    @Test
    void whenWriteEntries_thenSuccess() throws Exception {
        LedgerHandle lh = createLedger(bk, "myledger", ledgerPassword);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            byte[] data = new String("message-" + i).getBytes();
            lh.append(data);
        }
        lh.close();
        long elapsed = System.currentTimeMillis() - start;
        LOG.info("Entries added to ledgerId " + lh.getId() + ". count=1000, elapsed=" + elapsed);
    }

    @Test
    void whenWriteEntriesAsync_thenSuccess() throws Exception {
        CompletableFuture<Object> f = bk.newCreateLedgerOp()
          .withDigestType(DigestType.MAC)
          .withPassword(ledgerPassword)
          .execute()
          .thenApply((wh) -> {
              List<CompletableFuture<Long>> ops = new ArrayList<>();
              for (int i = 0; i < 1000; i++) {
                  byte[] data = String.format("message-%04d", i)
                    .getBytes();
                  ops.add(wh.appendAsync(data));
            }
            return CompletableFuture.allOf(ops.stream()
              .toArray(CompletableFuture[]::new))
              .thenCompose((v) -> wh.closeAsync());
        });
        f.get(5, TimeUnit.MINUTES);
    }

    @Test
    void whenWriteAndReadEntriesAsync_thenSuccess() throws Exception {
        CompletableFuture<Long> f = bk.newCreateLedgerOp()
          .withDigestType(DigestType.MAC)
          .withPassword(ledgerPassword)
          .execute()
          .thenApply((wh) -> {
              List<CompletableFuture<Long>> ops = new ArrayList<>();
              for (int i = 0; i < 1000; i++) {
                  byte[] data = String.format("message-%04d", i)
                    .getBytes();
                  ops.add(wh.appendAsync(data));
              }
              return CompletableFuture.allOf(ops.stream()
                .toArray(CompletableFuture[]::new))
                .thenCompose((v) -> wh.closeAsync())
                .thenApply((v) -> wh.getId());
          })
          .thenCompose((lf) -> lf); // flatten the futures
        Long ledgerId = f.get(5, TimeUnit.MINUTES);
        LOG.info("Ledger created with 1000 entries: ledgerId=" + ledgerId);

        // Now let's read data back...
        CompletableFuture<LedgerEntries> ef = bk.newOpenLedgerOp()
          .withLedgerId(ledgerId)
          .withPassword(ledgerPassword)
          .withDigestType(DigestType.MAC)
          .execute()
          .thenCompose((rh) -> {
              return rh.readLastAddConfirmedAsync()
                .thenCompose((lastId) -> rh.readAsync(0, lastId));
          });
        LedgerEntries entries = ef.get(5, TimeUnit.MINUTES);
        
        // Check all writes where OK
        long count = 0;
        Iterator<org.apache.bookkeeper.client.api.LedgerEntry> it = entries.iterator();
        while (it.hasNext()) {
            org.apache.bookkeeper.client.api.LedgerEntry e = it.next();
            String msg = new String(e.getEntryBytes());
            assertEquals(String.format("message-%04d", count), msg);
            count++;
        }
        assertEquals(1000, count);
        LOG.info("Got entries: count=" + count);
    }

    @Test
    void whenWriteAndReadEntries_thenSuccess() throws Exception {
        LedgerHandle lh = createLedger(bk, "myledger", ledgerPassword);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            byte[] data = new String("message-" + i).getBytes();
            lh.append(data);
        }
        lh.close();
        long elapsed = System.currentTimeMillis() - start;
        LOG.info("Entries added to ledgerId " + lh.getId() + ", elapsed=" + elapsed);

        Long ledgerId = findLedgerByName(bk, "myledger").orElse(null);
        assertNotNull(ledgerId);
        lh = bk.openLedger(ledgerId, BookKeeper.DigestType.MAC, ledgerPassword);
        long lastId = lh.readLastConfirmed();
        Enumeration<LedgerEntry> entries = lh.readEntries(0, lastId);
        while (entries.hasMoreElements()) {
            LedgerEntry entry = entries.nextElement();
            String msg = new String(entry.getEntry());
            LOG.info("Entry: id=" + entry.getEntryId() + ", data=" + msg);
        }
    }
}
