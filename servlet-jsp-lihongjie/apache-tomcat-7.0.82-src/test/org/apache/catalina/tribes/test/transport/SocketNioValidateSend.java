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
package org.apache.catalina.tribes.test.transport;

import java.math.BigDecimal;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.membership.MemberImpl;
import org.apache.catalina.tribes.transport.nio.NioSender;

public class SocketNioValidateSend {

    public static void main(String[] args) throws Exception {
        Selector selector;
        synchronized (Selector.class) {
            // Selector.open() isn't thread safe
            // http://bugs.sun.com/view_bug.do?bug_id=6427854
            // Affects 1.6.0_29, fixed in 1.7.0_01
            selector = Selector.open();
        }
        Member mbr = new MemberImpl("localhost", 9999, 0);
        byte seq = 0;
        byte[] buf = new byte[50000];
        Arrays.fill(buf,seq);
        int len = buf.length;
        BigDecimal total = new BigDecimal((double)0);
        BigDecimal bytes = new BigDecimal((double)len);
        NioSender sender = new NioSender();
        sender.setDestination(mbr);
        sender.setDirectBuffer(true);
        sender.setSelector(selector);
        sender.connect();
        sender.setMessage(buf);
        System.out.println("Writing to 9999");
        long start = 0;
        double mb = 0;
        boolean first = true;
        int count = 0;

        DecimalFormat df = new DecimalFormat("##.00");
        while (count<100000) {
            if (first) {
                first = false;
                start = System.currentTimeMillis();
            }
            sender.setMessage(buf);
            int selectedKeys = 0;
            try {
                selectedKeys = selector.select(0);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            if (selectedKeys == 0) {
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                it.remove();
                try {
                    int readyOps = sk.readyOps();
                    sk.interestOps(sk.interestOps() & ~readyOps);
                    if (sender.process(sk, false)) {
                        total = total.add(bytes);
                        sender.reset();
                        seq++;
                        Arrays.fill(buf,seq);
                        sender.setMessage(buf);
                        mb += ( (double) len) / 1024 / 1024;
                        if ( ( (++count) % 10000) == 0) {
                            long time = System.currentTimeMillis();
                            double seconds = ( (double) (time - start)) / 1000;
                            System.out.println("Throughput " + df.format(mb / seconds) + " MB/seconds, total "+mb+" MB, total "+total+" bytes.");
                        }
                    }

                } catch (Throwable t) {
                    t.printStackTrace();
                    return;
                }
            }
        }
        System.out.println("Complete, sleeping 15 seconds");
        Thread.sleep(15000);
    }
}
