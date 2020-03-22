package com.baeldung.guava.concurrency;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ListenableFutureExample {

    private static Logger logger = Logger.getAnonymousLogger();
    public static int quotient(int numerator, int denominator) {
        return Math.abs(numerator / denominator);
    }

    public static Integer compute(ListeningExecutorService service,
                                  ListenableFuture<Integer> future) {
        Integer[] value = new Integer[1];
        Futures.addCallback(future,
          new FutureCallback<Integer>() {
              public void onSuccess(Integer result) {
                  value[0] = result;
              }

              public void onFailure(Throwable t) {
                  value[0] = -1;
              }
          }, service);

        try {
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // handle appropriately
        }
        return value[0];
    }

    public static class EggCollector {
        private int id;

        public EggCollector(int id) {
            this.id = id + 1;
        }

        public int collectEgg() {
            boolean roomOpen = new Random().nextInt(100) < 50;
            int egg = roomOpen ? 1 : 0;
            logger.info("Robot-" + id + " collected " + (egg == 1 ? egg + " egg" : "no egg"));
            return egg;
        }
    }
}
