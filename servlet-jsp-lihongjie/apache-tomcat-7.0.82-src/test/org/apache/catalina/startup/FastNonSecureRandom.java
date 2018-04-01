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

import java.security.SecureRandom;
import java.util.Random;

public class FastNonSecureRandom extends SecureRandom {

    private static final long serialVersionUID = 1L;

    private final Random random = new Random();

    @Override
    public String getAlgorithm() {
        return "INSECURE";
    }

    @Override
    public synchronized void setSeed(byte[] seed) {
        // Not implemented
    }

    @Override
    public synchronized void setSeed(long seed) {
        // The super class constructor calls this method earlier than our
        // fields are initialized. Ignore the call.
        if (random == null) {
            return;
        }
        random.setSeed(seed);
    }

    @Override
    public synchronized void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }

    @Override
    public byte[] generateSeed(int numBytes) {
        byte[] value = new byte[numBytes];
        nextBytes(value);
        return value;
    }

}