package com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author VictorGil
 *
 * since March 2019
 */
public class ShutterImpl implements Shutter{
    private static final Logger log = LoggerFactory.getLogger(ShutterImpl.class);
    
    private List<Stoppable> stoppables = new LinkedList<Stoppable>();
    
    @Override
    public void shutdown() {
        log.info("We have been asked to shut down the application");
        
        Iterator<Stoppable> iter = stoppables.iterator();
        while(iter.hasNext()) {
            Stoppable stoppable = iter.next();
            stoppable.stop();
            if (iter.hasNext()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    log.error("Thread interrupted", ex);
                }
            }
        }        
    }
    
    public void addStoppable(Stoppable stoppable){
        stoppables.add(stoppable);
    }
}
