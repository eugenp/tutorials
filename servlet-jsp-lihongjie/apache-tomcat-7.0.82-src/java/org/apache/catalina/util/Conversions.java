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
package org.apache.catalina.util;

import java.io.IOException;

public class Conversions {

    private Conversions() {
        // Utility class. Hide default constructor.
    }

    public static long byteArrayToLong(byte[] input) throws IOException {
        if (input.length > 8) {
            // TODO: Better message
            throw new IOException();
        }

        int shift = 0;
        long result = 0;
        for (int i = input.length - 1; i >= 0; i--) {
            result = result + ((input[i] & 0xFF) << shift);
            shift += 8;
        }

        return result;
    }
}
