package com.baeldung.tutorials.bookkeeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.BookKeeper.DigestType;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.client.api.LedgerMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.AsyncCallback;

public class BkHelper {

    private static final Log log = LogFactory.getLog(BkHelper.class);

    public static BookKeeper createBkClient(String zkConnectionString) {
        try {
            return new BookKeeper(zkConnectionString);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a Ledger with the given name added as custom metadata
     * @param bk
     * @param name
     * @param password
     * @return
     */
    public static LedgerHandle createLedger(BookKeeper bk, String name, byte[] password) {
        try {
            return bk.createLedger(3, 2, 2, DigestType.MAC, password, Collections.singletonMap("name", name.getBytes()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Iterates over all available ledgers and returns the first one that has
     * a metadata key 'name' equals to the given name
     * @param bk
     * @param name
     * @return
     * @throws Exception
     */
    public static Optional<Long> findLedgerByName(BookKeeper bk, String name) throws Exception {

        Map<Long, LedgerMetadata> ledgers = new HashMap<Long, LedgerMetadata>();
        final AtomicInteger returnCode = new AtomicInteger(BKException.Code.OK);
        final CountDownLatch processDone = new CountDownLatch(1);

        // There's no standard "list" operation. Instead, BK offers a generalized way to
        // iterate over all available ledgers using an async visitor callback.
        // The second callback will be called when there are no more ledgers do process or if an
        // error occurs.
        bk.getLedgerManager()
          .asyncProcessLedgers((ledgerId, cb) -> collectLedgers(bk, ledgerId, cb, ledgers),
          (rc, s, obj) -> {
              returnCode.set(rc);
              processDone.countDown();
          }, null, BKException.Code.OK, BKException.Code.ReadException);

        processDone.await(5, TimeUnit.MINUTES);

        log.info("Ledgers collected: total found=" + ledgers.size());

        byte[] nameBytes = name.getBytes();

        Optional<Entry<Long, LedgerMetadata>> entry = ledgers.entrySet()
            .stream()
            .filter((e) -> {
                Map<String, byte[]> meta = e.getValue().getCustomMetadata();
                if (meta != null) {
                    log.info("ledger: " + e.getKey() + ", customMeta=" + meta);
                    byte[] data = meta.get("name");
                    if (data != null && Arrays.equals(data, nameBytes)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    log.info("ledger: " + e.getKey() + ", no meta");
                    return false;
                }
            })
            .findFirst();

        if (entry.isPresent()) {
            return Optional.of(entry.get().getKey());
        } else {
            return Optional.empty();
        }
    }

    public static void collectLedgers(BookKeeper bk, long ledgerId, AsyncCallback.VoidCallback cb, Map<Long, LedgerMetadata> ledgers) {
        log.debug("ledgerId: " + ledgerId);

        try {
            bk.getLedgerManager()
              .readLedgerMetadata(ledgerId)
              .thenAccept((v) -> {
                  log.debug("Got ledger metadata");
                  ledgers.put(ledgerId, v.getValue());
              })
              .thenAccept((v) -> {
                  cb.processResult(BKException.Code.OK, null, null);
              });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Return a list with all available Ledgers
     * @param bk
     * @return 
     */
    public static List<Long> listAllLedgers(BookKeeper bk) {
        
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
        
       try {
           processDone.await(1,TimeUnit.MINUTES);
           return ledgers;
       }
       catch(InterruptedException ie) {
           throw new RuntimeException(ie);
       }
    }


}
