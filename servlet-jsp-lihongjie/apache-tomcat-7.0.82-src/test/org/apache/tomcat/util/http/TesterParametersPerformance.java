/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.apache.tomcat.util.buf.B2CConverter;

public class TesterParametersPerformance {

    @Test
    public void testProcessParametersByteArrayIntInt() {
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);
        doTestProcessParametersMultiple("foo".getBytes());
    }

    private void doTestProcessParametersMultiple(byte[] input) {
        System.out.println(doTestProcessParameters(input, 10000));
        System.out.println(doTestProcessParameters(input, 20000));
        System.out.println(doTestProcessParameters(input, 40000));
        System.out.println(doTestProcessParameters(input, 80000));
        System.out.println(doTestProcessParameters(input, 160000));
        System.out.println(doTestProcessParameters(input, 320000));
        System.out.println(doTestProcessParameters(input, 640000));
        System.out.println(doTestProcessParameters(input, 1280000));
    }

    private long doTestProcessParameters(byte[] input, int size) {
        assertEquals(input.length, 3);

        Parameters p = new Parameters();

        byte[] params = createParams(input, size);
        //byte[] input = createParams(8);
        p.setEncoding("ISO-8859-1");
        long start = System.nanoTime();
        p.processParameters(params, 0, params.length);
        return System.nanoTime() - start;
    }

    private byte[] createParams(byte[] input, int len) {
        byte[] result = new byte[len * 4 - 1];

        for (int i = 0; i < len; i++) {
            result[i * 4] = input[0];
            result[i * 4 + 1] = input[1];
            result[i * 4 + 2] = input[2];
            if (i < len -1) {
                result[i * 4 + 3] = 38;
            }
        }
        return result;
    }

    @Test
    public void testCreateString() throws UnsupportedEncodingException {
        B2CConverter.getCharset("ISO-8859-1");
        doCreateStringMultiple("foo");
    }

    private void doCreateStringMultiple(String input) {
        System.out.println(doCreateString(input, 10, true));
        System.out.println(doCreateString(input, 100, true));
        System.out.println(doCreateString(input, 1000, true));
        System.out.println(doCreateString(input, 10000, true));
        System.out.println(doCreateString(input, 100000, true));
        System.out.println(doCreateString(input, 1000000, true));
        System.out.println(doCreateString(input, 2000000, true));
        //System.out.println(doCreateString(input, 4000000, true));
        //System.out.println(doCreateString(input, 8000000, true));
        System.out.println(doCreateString(input, 10, false));
        System.out.println(doCreateString(input, 100, false));
        System.out.println(doCreateString(input, 1000, false));
        System.out.println(doCreateString(input, 10000, false));
        System.out.println(doCreateString(input, 100000, false));
        System.out.println(doCreateString(input, 1000000, false));
        System.out.println(doCreateString(input, 2000000, false));
        //System.out.println(doCreateString(input, 4000000, false));
        //System.out.println(doCreateString(input, 8000000, false));
    }

    private long doCreateString(String input, int size,
            boolean defensiveCopyWorkAround) {
        int loops = 10000;
        byte[] inputBytes = input.getBytes();
        byte[] bytes = new byte[size];
        int inputLength = inputBytes.length;

        System.arraycopy(inputBytes, 0, bytes, 0, inputLength);

        String[] result = new String[loops];
        Charset charset = null;
        try {
            charset = B2CConverter.getCharset("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long start = System.nanoTime();
        for (int i = 0; i < loops; i++) {
            if (defensiveCopyWorkAround) {
                byte[] tmp = new byte[inputLength];
                System.arraycopy(bytes, 0, tmp, 0, inputLength);
                result[i] = new String(tmp, 0, inputLength, charset);
            } else {
                result[i] = new String(bytes, 0, inputLength, charset);
            }
        }

        return System.nanoTime() - start;
    }
}
