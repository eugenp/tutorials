package com.baeldung.concurrent.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

class ParkingThreadDemo {
    private static final Logger LOG = LoggerFactory.getLogger(ParkingThreadDemo.class);

    public void parkingThread throws

    InterruptedException {
        final ExecutorService executorPool = Executors.newFixedThreadPool(5);
        final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<Long>();
        Collection<Future<Long>> collection = new ArrayList<Future<Long>>();

        // consumer:
        for (int i = 0; i < 20; i++) {
            collection.add(executorPool.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    while (true) {
                        Long item = queue.take();
                        if (item.intValue() == 0) {
                            break;
                        }
                    }
                    return 1L;
                }
            }));
        }
        // producer:
        for (int i = 0; i < 20; i++) {
            collection.add(executorPool.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    for (int i = 100; i >= 0; i--) {
                        queue.put((long) i);
                    }
                    return -1L;
                }
            }));
        }
        executorPool.shutdown();
        LOG.info("Collection size = " + collection.size());
    }
}
