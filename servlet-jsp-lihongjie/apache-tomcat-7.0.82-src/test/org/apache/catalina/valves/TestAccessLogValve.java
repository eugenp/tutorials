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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

public class TestAccessLogValve {

    // Note that there is a similar test:
    // org.apache.juli.TestDateFormatCache.testBug54044()
    @Test
    public void testBug54044() throws Exception {

        final int cacheSize = 10;

        SimpleDateFormat sdf =
                new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss Z]", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());

        AccessLogValve.DateFormatCache dfc =
                new AccessLogValve.DateFormatCache(
                        cacheSize, Locale.US, null);

        // Create an array to hold the expected values
        String[] expected = new String[cacheSize];

        // Fill the cache & populate the expected values
        for (int secs = 0; secs < (cacheSize); secs++) {
            dfc.getFormat(secs * 1000);
            expected[secs] = generateExpected(sdf, secs);
        }
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);


        // Cause the cache to roll-around by one and then confirm
        dfc.getFormat(cacheSize * 1000);
        expected[0] = generateExpected(sdf, cacheSize);
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);

        // Jump 2 ahead and then confirm (skipped value should be null)
        dfc.getFormat((cacheSize + 2)* 1000);
        expected[1] = null;
        expected[2] = generateExpected(sdf, cacheSize + 2);
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);

        // Back 1 to fill in the gap
        dfc.getFormat((cacheSize + 1)* 1000);
        expected[1] = generateExpected(sdf, cacheSize + 1);
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);

        // Return to 1 and confirm skipped value is null
        dfc.getFormat(1 * 1000);
        expected[1] = generateExpected(sdf, 1);
        expected[2] = null;
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);

        // Go back one further
        dfc.getFormat(0);
        expected[0] = generateExpected(sdf, 0);
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);

        // Jump ahead far enough that the entire cache will need to be cleared
        dfc.getFormat(42 * 1000);
        for (int i = 0; i < cacheSize; i++) {
            expected[i] = null;
        }
        expected[0] = generateExpected(sdf, 42);
        Assert.assertArrayEquals(expected, dfc.cLFCache.cache);
    }

    private String generateExpected(SimpleDateFormat sdf, long secs) {
        return sdf.format(new Date(secs * 1000));
    }
}
