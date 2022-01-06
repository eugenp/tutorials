package com.baeldung.volatilekeywordthreadsafety;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class VolatileVarNotThreadSafe {

    private static final Logger LOG = LoggerFactory.getLogger(VolatileVarNotThreadSafe.class);
    private static volatile int count = 0;
    private static final int MAX_LIMIT = 1000;
    
    public static void increment() {
        count++;
    }
    
    public static int getCount() {
        return count;
    }
    
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
           @Override
           public void run() {
               for(int index=0; index<MAX_LIMIT; index++) {
                   increment();
               }
           }
        });
        
        Thread t2 = new Thread(new Runnable() {
           @Override
           public void run() {
               for(int index=0; index<MAX_LIMIT; index++) {
                   increment();
               }
           }
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();
        
        LOG.info("value of counter variable: "+count);
    }
}
