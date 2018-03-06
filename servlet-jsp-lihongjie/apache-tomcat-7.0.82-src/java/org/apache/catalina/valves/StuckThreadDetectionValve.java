/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.valves;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 * This valve allows to detect requests that take a long time to process, which
 * might indicate that the thread that is processing it is stuck.
 */
public class StuckThreadDetectionValve extends ValveBase {

    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
            "org.apache.catalina.valves.StuckThreadDetectionValve/1.0";
    /**
     * Logger
     */
    private static final Log log = LogFactory.getLog(StuckThreadDetectionValve.class);

    /**
     * The string manager for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    /**
     * Keeps count of the number of stuck threads detected
     */
    private final AtomicInteger stuckCount = new AtomicInteger(0);

    /**
     * Keeps count of the number of stuck threads that have been interrupted
     */
    private AtomicLong interruptedThreadsCount = new AtomicLong();

    /**
     * In seconds. Default 600 (10 minutes).
     */
    private int threshold = 600;

    /**
     * In seconds. Default is -1 to disable interruption.
     */
    private int interruptThreadThreshold;

    /**
     * The only references we keep to actual running Thread objects are in
     * this Map (which is automatically cleaned in invoke()s finally clause).
     * That way, Threads can be GC'ed, even though the Valve still thinks they
     * are stuck (caused by a long monitor interval)
     */
    private final Map<Long, MonitoredThread> activeThreads =
            new ConcurrentHashMap<Long, MonitoredThread>();

    private final Queue<CompletedStuckThread> completedStuckThreadsQueue =
            new ConcurrentLinkedQueue<CompletedStuckThread>();

    /**
     * Specifies the threshold (in seconds) used when checking for stuck threads.
     * If &lt;=0, the detection is disabled. The default is 600 seconds.
     *
     * @param threshold
     *            The new threshold in seconds
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * @see #setThreshold(int)
     * @return The current threshold in seconds
     */
    public int getThreshold() {
        return threshold;
    }


    public int getInterruptThreadThreshold() {
        return interruptThreadThreshold;
    }

    /**
     * Specifies the threshold (in seconds) before stuck threads are interrupted.
     * If &lt;=0, the interruption is disabled. The default is -1.
     * If &gt;=0, the value must actually be &gt;= threshold.
     *
     * @param interruptThreadThreshold
     *            The new thread interruption threshold in seconds
     */
    public void setInterruptThreadThreshold(int interruptThreadThreshold) {
        this.interruptThreadThreshold = interruptThreadThreshold;
    }

    /**
     * Required to enable async support.
     */
    public StuckThreadDetectionValve() {
        super(true);
    }


    @Override
    protected void initInternal() throws LifecycleException {
        super.initInternal();

        if (log.isDebugEnabled()) {
            log.debug("Monitoring stuck threads with threshold = "
                    + threshold
                    + " sec");
        }
    }

    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return info;
    }

    private void notifyStuckThreadDetected(MonitoredThread monitoredThread,
        long activeTime, int numStuckThreads) {
        if (log.isWarnEnabled()) {
            String msg = sm.getString(
                "stuckThreadDetectionValve.notifyStuckThreadDetected",
                monitoredThread.getThread().getName(),
                Long.valueOf(activeTime),
                monitoredThread.getStartTime(),
                Integer.valueOf(numStuckThreads),
                monitoredThread.getRequestUri(),
                Integer.valueOf(threshold),
                String.valueOf(monitoredThread.getThread().getId())
                );
            // msg += "\n" + getStackTraceAsString(trace);
            Throwable th = new Throwable();
            th.setStackTrace(monitoredThread.getThread().getStackTrace());
            log.warn(msg, th);
        }
    }

    private void notifyStuckThreadCompleted(CompletedStuckThread thread,
            int numStuckThreads) {
        if (log.isWarnEnabled()) {
            String msg = sm.getString(
                "stuckThreadDetectionValve.notifyStuckThreadCompleted",
                thread.getName(),
                Long.valueOf(thread.getTotalActiveTime()),
                Integer.valueOf(numStuckThreads),
                String.valueOf(thread.getId()));
            // Since the "stuck thread notification" is warn, this should also
            // be warn
            log.warn(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invoke(Request request, Response response)
            throws IOException, ServletException {

        if (threshold <= 0) {
            // short-circuit if not monitoring stuck threads
            getNext().invoke(request, response);
            return;
        }

        // Save the thread/runnable
        // Keeping a reference to the thread object here does not prevent
        // GC'ing, as the reference is removed from the Map in the finally clause

        Long key = Long.valueOf(Thread.currentThread().getId());
        StringBuffer requestUrl = request.getRequestURL();
        if(request.getQueryString()!=null) {
            requestUrl.append("?");
            requestUrl.append(request.getQueryString());
        }
        MonitoredThread monitoredThread = new MonitoredThread(Thread.currentThread(),
            requestUrl.toString(), interruptThreadThreshold > 0);
        activeThreads.put(key, monitoredThread);

        try {
            getNext().invoke(request, response);
        } finally {
            activeThreads.remove(key);
            if (monitoredThread.markAsDone() == MonitoredThreadState.STUCK) {
                if(monitoredThread.wasInterrupted()) {
                    interruptedThreadsCount.incrementAndGet();
                }
                completedStuckThreadsQueue.add(
                        new CompletedStuckThread(monitoredThread.getThread(),
                            monitoredThread.getActiveTimeInMillis()));
            }
        }
    }

    @Override
    public void backgroundProcess() {
        super.backgroundProcess();

        long thresholdInMillis = threshold * 1000L;

        // Check monitored threads, being careful that the request might have
        // completed by the time we examine it
        for (MonitoredThread monitoredThread : activeThreads.values()) {
            long activeTime = monitoredThread.getActiveTimeInMillis();

            if (activeTime >= thresholdInMillis && monitoredThread.markAsStuckIfStillRunning()) {
                int numStuckThreads = stuckCount.incrementAndGet();
                notifyStuckThreadDetected(monitoredThread, activeTime, numStuckThreads);
            }
            if(interruptThreadThreshold > 0 && activeTime >= interruptThreadThreshold*1000L) {
                monitoredThread.interruptIfStuck(interruptThreadThreshold);
            }
        }
        // Check if any threads previously reported as stuck, have finished.
        for (CompletedStuckThread completedStuckThread = completedStuckThreadsQueue.poll();
            completedStuckThread != null; completedStuckThread = completedStuckThreadsQueue.poll()) {

            int numStuckThreads = stuckCount.decrementAndGet();
            notifyStuckThreadCompleted(completedStuckThread, numStuckThreads);
        }
    }

    public int getStuckThreadCount() {
        return stuckCount.get();
    }

    public long[] getStuckThreadIds() {
        List<Long> idList = new ArrayList<Long>();
        for (MonitoredThread monitoredThread : activeThreads.values()) {
            if (monitoredThread.isMarkedAsStuck()) {
                idList.add(Long.valueOf(monitoredThread.getThread().getId()));
            }
        }

        long[] result = new long[idList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = idList.get(i).longValue();
        }
        return result;
    }

    public String[] getStuckThreadNames() {
        List<String> nameList = new ArrayList<String>();
        for (MonitoredThread monitoredThread : activeThreads.values()) {
            if (monitoredThread.isMarkedAsStuck()) {
                nameList.add(monitoredThread.getThread().getName());
            }
        }
        return nameList.toArray(new String[nameList.size()]);
    }

    public long getInterruptedThreadsCount() {
        return interruptedThreadsCount.get();
    }


    private static class MonitoredThread {

        /**
         * Reference to the thread to get a stack trace from background task
         */
        private final Thread thread;
        private final String requestUri;
        private final long start;
        private final AtomicInteger state = new AtomicInteger(
            MonitoredThreadState.RUNNING.ordinal());
        /**
         * Semaphore to synchronize the stuck thread with the background-process
         * thread. It's not used if the interruption feature is not active.
         */
        private final Semaphore interruptionSemaphore;
        /**
         * Set to true after the thread is interrupted. No need to make it
         * volatile since it is accessed right after acquiring the semaphore.
         */
        private boolean interrupted;

        public MonitoredThread(Thread thread, String requestUri,
                boolean interruptible) {
            this.thread = thread;
            this.requestUri = requestUri;
            this.start = System.currentTimeMillis();
            if (interruptible) {
                interruptionSemaphore = new Semaphore(1);
            } else {
                interruptionSemaphore = null;
            }
        }

        public Thread getThread() {
            return this.thread;
        }

        public String getRequestUri() {
            return requestUri;
        }

        public long getActiveTimeInMillis() {
            return System.currentTimeMillis() - start;
        }

        public Date getStartTime() {
            return new Date(start);
        }

        public boolean markAsStuckIfStillRunning() {
            return this.state.compareAndSet(MonitoredThreadState.RUNNING.ordinal(),
                MonitoredThreadState.STUCK.ordinal());
        }

        public MonitoredThreadState markAsDone() {
            int val = this.state.getAndSet(MonitoredThreadState.DONE.ordinal());
            MonitoredThreadState threadState = MonitoredThreadState.values()[val];

            if (threadState == MonitoredThreadState.STUCK
                    && interruptionSemaphore != null) {
                try {
                    // use the semaphore to synchronize with the background thread
                    // which might try to interrupt this current thread.
                    // Otherwise, the current thread might be interrupted after
                    // going out from here, maybe already serving a new request
                    this.interruptionSemaphore.acquire();
                } catch (InterruptedException e) {
                    log.debug(
                            "thread interrupted after the request is finished, ignoring",
                            e);
                }
                // no need to release the semaphore, it will be GCed
            }
            //else the request went through before being marked as stuck, no need
            //to sync agains the semaphore
            return threadState;
        }

        boolean isMarkedAsStuck() {
            return this.state.get() == MonitoredThreadState.STUCK.ordinal();
        }

        public boolean interruptIfStuck(long interruptThreadThreshold) {
            if (!isMarkedAsStuck() || interruptionSemaphore == null
                    || !this.interruptionSemaphore.tryAcquire()) {
                // if the semaphore is already acquired, it means that the
                // request thread got unstuck before we interrupted it
                return false;
            }
            try {
                if (log.isWarnEnabled()) {
                    String msg = sm.getString(
                        "stuckThreadDetectionValve.notifyStuckThreadInterrupted",
                        this.getThread().getName(),
                        Long.valueOf(getActiveTimeInMillis()),
                        this.getStartTime(), this.getRequestUri(),
                        Long.valueOf(interruptThreadThreshold),
                        String.valueOf(this.getThread().getId()));
                    Throwable th = new Throwable();
                    th.setStackTrace(this.getThread().getStackTrace());
                    log.warn(msg, th);
                }
                this.thread.interrupt();
            } finally {
                this.interrupted = true;
                this.interruptionSemaphore.release();
            }
            return true;
        }

        public boolean wasInterrupted() {
            return interrupted;
        }
    }

    private static class CompletedStuckThread {

        private final String threadName;
        private final long threadId;
        private final long totalActiveTime;

        public CompletedStuckThread(Thread thread, long totalActiveTime) {
            this.threadName = thread.getName();
            this.threadId = thread.getId();
            this.totalActiveTime = totalActiveTime;
        }

        public String getName() {
            return this.threadName;
        }

        public long getId() {
            return this.threadId;
        }

        public long getTotalActiveTime() {
            return this.totalActiveTime;
        }
    }

    private enum MonitoredThreadState {
        RUNNING, STUCK, DONE;
    }
}
