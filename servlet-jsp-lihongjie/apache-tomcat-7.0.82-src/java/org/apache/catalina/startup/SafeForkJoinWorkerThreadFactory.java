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
package org.apache.catalina.startup;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * Provides a {@link ForkJoinWorkerThreadFactory} that provides {@link
 * ForkJoinWorkerThread}s that won't trigger memory leaks due to retained
 * references to web application class loaders.
 * <p>
 * Note: This class must be available on the boot strap class path for it to be
 * visible to {@link ForkJoinPool}.
 * <p>
 * Note: This a helper class that is used by memory leak protection code
 * ({@code JreMemoryLeakPreventionListener}) to workaround a bug in
 * Oracle Java 7 / Java 8 Java Runtime. See
 * <a href="https://bz.apache.org/bugzilla/show_bug.cgi?id=60620">bug 60620</a>
 * for discussion links.
 *
 * As {@code ForkJoinPool.ForkJoinWorkerThreadFactory} class is available since
 * Java 7 only, compilation of this class is skipped when building Tomcat with Java 6.
 */
public class SafeForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory {

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new SafeForkJoinWorkerThread(pool);
    }


    private static class SafeForkJoinWorkerThread extends ForkJoinWorkerThread {

        protected SafeForkJoinWorkerThread(ForkJoinPool pool) {
            super(pool);
            setContextClassLoader(ForkJoinPool.class.getClassLoader());
        }
    }
}