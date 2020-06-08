package com.baeldung.tutorials.bookkeeper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.bookkeeper.client.AsyncCallback.AddCallback;
import org.apache.bookkeeper.client.AsyncCallback.CreateCallback;
import org.apache.bookkeeper.client.AsyncCallback.ReadCallback;
import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.client.api.ReadHandle;
import org.apache.bookkeeper.client.api.WriteAdvHandle;
import org.apache.bookkeeper.client.api.WriteHandle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.AsyncCallback;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BkHelperIntegrationTest extends BkHelper {
    
    private static BookKeeper bk;
    private byte[] ledgerPassword = "SuperS3cR37".getBytes();
    
    private static final Log log = LogFactory.getLog(BkHelperIntegrationTest.class);
    
    @BeforeAll
    static void initBkClient() {
        bk = createBkClient("192.168.99.101:2181");         
    }
    
    @Test
    void whenCreateLedger_thenSuccess() throws Exception {
        
        LedgerHandle lh = bk.createLedger(BookKeeper.DigestType.MAC, ledgerPassword);
        assertNotNull(lh);
        assertNotNull(lh.getId());
        
        CreateCallback cb = (rc, ll, ctx) -> {
            
        };
        
        bk.asyncCreateLedger(3, 2, 2, BookKeeper.DigestType.MAC, "passwd".getBytes(), cb, null, Collections.emptyMap());
        //lh.get
        
//        CompletableFuture<WriteAdvHandle> cf = bk.newCreateLedgerOp()
//          .withDigestType(org.apache.bookkeeper.client.api.DigestType.MAC)
//          .withPassword("password".getBytes())
//         .makeAdv()
//          .execute();
        
        log.info("[I33] Ledge created: id=" + lh.getId());
    }
    
    @Test
    void whenListLedgers_thenSuccess() throws Exception {
        
        final AtomicInteger returnCode = new AtomicInteger(BKException.Code.OK);
        final CountDownLatch processDone = new CountDownLatch(1);     
        
        // There's no standard "list" operation. Instead, BK offers a generalized way to
        // iterate over all available ledgers using an async visitor callback.
        // The second callback will be called when there are no more ledgers do process or if an
        // error occurs.
        bk.getLedgerManager()
          .asyncProcessLedgers(
            (data,cb) -> processLedger(data,cb), 
            (rc, s, obj) -> {
                  returnCode.set(rc);
                  processDone.countDown();
            }, 
            null, 
            BKException.Code.OK, 
            BKException.Code.ReadException);  
        
        processDone.await(5, TimeUnit.MINUTES);
    }
    
    @Test
    void whenWriteEntries_thenSuccess() throws Exception {
        
        LedgerHandle lh = createLedger(bk,"myledger",ledgerPassword);
                
        long start = System.currentTimeMillis();
        for ( int i = 0 ; i < 1000 ; i++ ) {
            byte[] data = new String("message-" + i).getBytes();
            lh.append(data);
        } 
        
        byte[] data = new byte[] {};
        
        CompletableFuture<Long> f = lh.appendAsync(data);
        AddCallback cbw = (rc,ll,entryId,ctx) -> {
            
        };
        
        lh.asyncAddEntry(data, cbw, null);
        
        lh.close();
        long elapsed = System.currentTimeMillis() - start;
        log.info("Entries added to ledgerId " + lh.getId() + ". count=1000, elapsed=" + elapsed);
        
        
    }

    @Test
    void whenWriteAndReadEntries_thenSuccess() throws Exception {
        
        LedgerHandle lh = createLedger(bk,"myledger",ledgerPassword);
        
        long start = System.currentTimeMillis();
        for ( int i = 0 ; i < 1000 ; i++ ) {
            byte[] data = new String("message-" + i).getBytes();
            lh.append(data);
        } 
        
        lh.close();
        long elapsed = System.currentTimeMillis() - start;
        log.info("Entries added to ledgerId " + lh.getId() + ", elapsed=" + elapsed);       
        
        Long ledgerId = findLedgerByName(bk,"myledger").orElse(null);
        assertNotNull(ledgerId);
        
        lh = bk.openLedger(ledgerId, BookKeeper.DigestType.MAC, ledgerPassword);
        long lastId = lh.readLastConfirmed();
        Enumeration<LedgerEntry> entries = lh.readEntries(0, lastId);

        ReadCallback cbr;
        lh.asyncReadEntries(0, lastId,
        (rc,ledgerHandle,ee,ctx) -> {
            while(ee.hasMoreElements()) {
                LedgerEntry e = ee.nextElement();
            }
        }, null);
        
        ReadHandle rh = bk.newOpenLedgerOp()
          .withLedgerId(ledgerId)
          .withDigestType(DigestType.MAC)
          .withPassword("password".getBytes())
          .execute().get();
        
        rh.read(0, lastId).forEach((entry) -> {
            
        });
        
        rh.readAsync(0, lastId).thenAccept((ee) -> {
           ee.forEach((entry) -> {
              // .. 
           });
        });
        
        while(entries.hasMoreElements()) {
            LedgerEntry entry = entries.nextElement();
            String msg = new String(entry.getEntry());
            log.info("Entry: id=" + entry.getEntryId() + ", data=" + msg);
        }
        
    }
    
    private void processLedger(long ledgerId, AsyncCallback.VoidCallback cb) {
        log.info("ledgerId: " + ledgerId);
        cb.processResult(BKException.Code.OK, null, null);
    }

    
    private CompletableFuture<List<Long>> listAllLedgers(BookKeeper bk) {
        
        final List<Long> ledgers = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch processDone = new CountDownLatch(1);     
        
        bk.getLedgerManager()
          .asyncProcessLedgers(
            (ledgerId,cb) -> {
                ledgers.add(ledgerId);
                cb.processResult(BKException.Code.OK, null, null);
            }, 
            (rc, s, obj) -> {
                processDone.countDown();
          },
          null, BKException.Code.OK, BKException.Code.ReadException);
        
        CompletableFuture<List<Long>> cf = CompletableFuture.supplyAsync(() -> {
           try {
               processDone.await(1,TimeUnit.MINUTES);
               return ledgers;
           }
           catch(InterruptedException ie) {
               throw new RuntimeException(ie);
           }
        });
        
        return cf;
    }

    
    
}
