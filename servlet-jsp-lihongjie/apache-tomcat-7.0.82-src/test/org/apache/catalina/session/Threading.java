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
package org.apache.catalina.session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * The design of the session manager depends on the thread-safety - or not - of
 * a number of classes. In some cases the Javadoc is unclear on the
 * thread-safety of a class. These tests were written to test the thread-safety
 * of key classes.
 *
 * Named Threading so it is not automatically executed as part of the unit
 * tests.
 */
public class Threading {

    /**
     * {@link FileInputStream#read(byte[])} and related methods are all native
     * methods so it isn't immediately obvious if they are thread-safe or not.
     *
     * <pre>
     * Windows JDK 1.6.0_22_x64 - Thread safe
     * OSX     JDK 1.6.0_22_x64 - Not thread safe
     * </pre>
     *
     * Therefore, have to assume that {@link FileInputStream#read(byte[])} is
     * not thread safe.
     */
    @Test
    public void testFileInputStream() throws Exception {
        doTestFileInputStream(1);
        doTestFileInputStream(2);
        doTestFileInputStream(4);
        doTestFileInputStream(16);
    }

    public void doTestFileInputStream(int threadCount) throws Exception {

        // Assumes "ant release" has been run
        // Will need to be updated as new releases are made
        File file = new File(
                "./output/release/v7.0.20-dev/bin/apache-tomcat-7.0.20-dev.zip");

        FileInputStream fis = new FileInputStream(file);

        Thread[] threads = new Thread[threadCount];
        FisReaderThread[] runnables = new FisReaderThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            runnables[i] = new FisReaderThread(fis);
            threads[i] = new Thread(runnables[i]);
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
                if (runnables[i].isfailed()) {
                    fail();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                fail(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();

        long byteCount = 0;
        for (int i = 0; i < threadCount; i++) {
            byteCount += runnables[i].getByteCount();
        }

        StringBuilder result = new StringBuilder();
        result.append("Threads: ");
        result.append(threadCount);
        result.append(", Time(ms): ");
        result.append(end-start);
        result.append(", Bytes: ");
        result.append(byteCount);
        System.out.println(result.toString());
    }

    private static final class FisReaderThread implements Runnable {

        private FileInputStream fis;
        // Small buffer to make the process slow
        private byte[] buffer = new byte[4];
        private long byteCount = 0;
        private boolean fail = false;

        public FisReaderThread(FileInputStream fis) {
            this.fis = fis;
        }

        @Override
        public void run() {
            int read = 0;
            while (read > -1) {
                byteCount += read;
                try {
                    // Uncomment the sync block to test adding the sync fixes
                    // issues on platforms where fis is not thread-safe
                    // synchronized (fis) {
                        read = fis.read(buffer);
                    //}
                } catch (IOException e) {
                    e.printStackTrace();
                    fail = true;
                    read = -1;
                }
            }
        }

        public long getByteCount() {
            return byteCount;
        }

        public boolean isfailed() {
            return fail;
        }
    }
}
