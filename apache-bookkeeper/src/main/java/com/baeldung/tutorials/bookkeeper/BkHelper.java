package com.baeldung.tutorials.bookkeeper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import org.apache.bookkeeper.conf.ClientConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.AsyncCallback;

public class BkHelper {
    
    private static final Log log = LogFactory.getLog(BkHelper.class);
    
    public static BookKeeper createBkClient(String zkConnectionString) {
        try {
            ClientConfiguration cfg = new ClientConfiguration();
            cfg.setMetadataServiceUri("zk+null://zookeeper-host:2131");
            BookKeeper.forConfig(cfg).build();
            
            
            return new BookKeeper(zkConnectionString);
        }
        catch(Exception ex) {
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
            
            return bk.createLedger(3,2,2,
              DigestType.MAC,
              password,
              Collections.singletonMap("name", name.getBytes()));
        }
        catch(Exception ex) {
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
        
        Map<Long,LedgerMetadata> ledgers = new HashMap<Long, LedgerMetadata>();
        final AtomicInteger returnCode = new AtomicInteger(BKException.Code.OK);
        final CountDownLatch processDone = new CountDownLatch(1);     
        
        // There's no standard "list" operation. Instead, BK offers a generalized way to
        // iterate over all available ledgers using an async visitor callback.
        // The second callback will be called when there are no more ledgers do process or if an
        // error occurs.
        bk.getLedgerManager()
          .asyncProcessLedgers(
            (data,cb) -> collectLedgers(bk,data,cb,ledgers), 
            (rc, s, obj) -> {
                  returnCode.set(rc);
                  processDone.countDown();
            }, 
            null, 
            BKException.Code.OK, 
            BKException.Code.ReadException);  
        
        processDone.await(5, TimeUnit.MINUTES);
        
        log.info("Ledgers collected: total found=" + ledgers.size());
        
        byte[] nameBytes = name.getBytes();
        
        Optional<Entry<Long, LedgerMetadata>> entry = ledgers.entrySet().stream().filter((e) -> {
            Map<String,byte[]> meta = e.getValue().getCustomMetadata();
            if ( meta != null ) {
                log.info("ledger: " + e.getKey() + ", customMeta=" + meta);
                byte[] data =  meta.get("name");
                if ( data != null && Arrays.equals(data, nameBytes)) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                log.info("ledger: " + e.getKey() + ", no meta");                
                return false;
            }
        })
        .findFirst();
        
        
        if (entry.isPresent()) {
            return Optional.of(entry.get().getKey());
        }
        else {
            return Optional.empty();
        }      
    }
    
    public static  void collectLedgers(BookKeeper bk, long ledgerId, AsyncCallback.VoidCallback cb, Map<Long,LedgerMetadata> ledgers) {
        log.info("ledgerId: " + ledgerId);
        
        try {
            bk.getLedgerManager()
              .readLedgerMetadata(ledgerId)
              .thenAccept((v) -> {
                  log.info("Got ledger metadata");
                  ledgers.put(ledgerId,v.getValue());  
              })
              .thenAccept((v) -> {
                  cb.processResult(BKException.Code.OK, null, null);
              });
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    

}
