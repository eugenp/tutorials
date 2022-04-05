package com.baeldung.concurrent.stopexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class StopExecution {
    private static final Logger LOG = LoggerFactory.getLogger(StopExecution.class);

    public static void main(String[] args) {
        StopExecution stopExecution = new StopExecution();
        //stopExecution.testUsingLoop();
        //stopExecution.testTimer();
        stopExecution.testScheduledExecutor();
        LOG.info("done");
    }

    public void testUsingLoop() {
        long start = System.currentTimeMillis();
        long end = start + 5000;
        List<String> items = new ArrayList<>();
        int counter = 0;

        // Let this loop run only upto 5 seconds
        while (System.currentTimeMillis() < end && counter < items.size()) {
            // Fetch the item from the list.
            // Some expensive operation on the item.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }
    }

    public static void testThreads() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("inside run");

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOG.info("exit run");
            }
        });
        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            LOG.info(thread.getState().name());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testExecutor() {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> f = null;
        try {
            f = service.submit(() -> {
                // Do you long running calculation here
                try {
                    Thread.sleep(2737); // Simulate some delay
                } catch (InterruptedException e) {
                    LOG.info("Interrupted");
                    return "interrupted";
                }
                LOG.info("Sleep finished");
                return "42";
            });

            LOG.info(f.get(2, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            f.cancel(true);
            LOG.error("Calculation took to long");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
    }

    public void testExecutor2() {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        Future f = null;
        try {
            f = service.submit(new LongRunningTask());
            LOG.info("testExecutor2");
            f.get(1, TimeUnit.SECONDS);
        } catch (final TimeoutException e) {
            f.cancel(true);
            LOG.error("Calculation took to long");
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdownNow();
        }
    }

    public void testScheduledExecutor() {
        LOG.info("testScheduledExecutor");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        Future future = executor.submit(new LongRunningTask());
        executor.schedule(new Runnable() {
            public void run() {
                future.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    public void testThreadAndInterrupt() {

        Thread t;
        try {
            t = new Thread(new LongRunningTask());

            LOG.info("testExecutor3");
            long end = System.currentTimeMillis() + 2000;
            t.start();
            while (t.isAlive() && System.currentTimeMillis() < end) {
                Thread.sleep(50);
            }
            t.interrupt();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void testTimer() {
        LOG.info("Timer test");
        Thread t = new Thread(new LongRunningTask());
        Timer timeoutTimer = new Timer();
        timeoutTimer.schedule(new TimeOutTask(t, timeoutTimer), 1000);
        t.start();
    }

    class MyRunnableTask implements Runnable {
        public void run() {
            try {
                LOG.info("MyRunnable...");
                Thread.sleep(10000);
            } catch (InterruptedException ie) {
                LOG.info("MyRunnable interrupted...");
            }
        }
    }

    class TimeOutTask extends TimerTask {
        private Thread t;
        private Timer timer;

        TimeOutTask(Thread t, Timer timer) {
            this.t = t;
            this.timer = timer;
        }

        public void run() {
            if (t != null && t.isAlive()) {
                t.interrupt();
                timer.cancel();
            }
        }
    }

    class LongRunningTask implements Runnable {
        @Override
        public void run() {
            longRunningSort();
        }

        private void longRunningSort() {
            LOG.info("Long running task started");
            int len = 100000;
            List<Integer> numbers = new ArrayList<>();
            try {
                for (int i = len; i > 0; i--) {
                    //Thread.sleep(5)
                    numbers.add(i);
                }

                int i = 0;
                for (i = 0; i < len; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < len; j++) {
                        if (numbers.get(minIndex) > numbers.get(j))
                            minIndex = j;
                    }
                    if (minIndex != i) {
                        int temp = numbers.get(i);
                        numbers.set(i, numbers.get(minIndex));
                        numbers.set(minIndex, temp);
                    }
                    throwExceptionOnThreadInterrupt();
                }
                LOG.info("Index position: " + i);
                LOG.info("Long running task finished");
            } catch (InterruptedException e) {
                LOG.info("Long running operation interrupted");
            }
        }

        private void throwExceptionOnThreadInterrupt() throws InterruptedException {
            if (Thread.currentThread().interrupted()) {
                throw new InterruptedException();
            }
        }
    }

}


