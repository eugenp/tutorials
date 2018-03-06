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
package org.apache.tomcat.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A thread safe wrapper around {@link MessageDigest} that does not make use
 * of ThreadLocal and - broadly - only creates enough MessageDigest objects
 * to satisfy the concurrency requirements.
 */
public class ConcurrentMessageDigest {

    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA-1";

    private static final Map<String,Queue<MessageDigest>> queues =
            new HashMap<String,Queue<MessageDigest>>();


    private ConcurrentMessageDigest() {
        // Hide default constructor for this utility class
    }

    static {
        try {
            // Init commonly used algorithms
            init(MD5);
            init(SHA1);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] digestMD5(byte[]... input) {
        return digest(MD5, input);
    }

    public static byte[] digestSHA1(byte[]... input) {
        return digest(SHA1, input);
    }

    public static byte[] digest(String algorithm, byte[]... input) {

        Queue<MessageDigest> queue = queues.get(algorithm);
        if (queue == null) {
            throw new IllegalStateException("Must call init() first");
        }

        MessageDigest md = queue.poll();
        if (md == null) {
            try {
                md = MessageDigest.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                // Ignore. Impossible if init() has been successfully called
                // first.
                throw new IllegalStateException("Must call init() first");
            }
        }

        for (byte[] bytes : input) {
            md.update(bytes);
        }
        byte[] result = md.digest();

        queue.add(md);

        return result;
    }


    /**
     * Ensures that {@link #digest(String, byte[][])} will support the specified
     * algorithm. This method <b>must</b> be called and return successfully
     * before using {@link #digest(String, byte[][])}.
     *
     * @param algorithm The message digest algorithm to be supported
     *
     * @throws NoSuchAlgorithmException If the algorithm is not supported by the
     *                                  JVM
     */
    public static void init(String algorithm) throws NoSuchAlgorithmException {
        synchronized (queues) {
            if (!queues.containsKey(algorithm)) {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                Queue<MessageDigest> queue =
                        new ConcurrentLinkedQueue<MessageDigest>();
                queue.add(md);
                queues.put(algorithm, queue);
            }
        }
    }
}
