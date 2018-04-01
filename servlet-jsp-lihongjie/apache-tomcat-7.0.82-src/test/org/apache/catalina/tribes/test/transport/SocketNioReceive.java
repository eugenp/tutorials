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

import java.text.DecimalFormat;

import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MessageListener;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.membership.MemberImpl;
import org.apache.catalina.tribes.transport.nio.NioReceiver;

public class SocketNioReceive {
    static int count = 0;
    static int accept = 0;
    static long start = 0;
    static double mb = 0;
    static int len = 0;
    static DecimalFormat df = new DecimalFormat("##.00");
    static double seconds = 0;

    protected static Object mutex = new Object();
    public static void main(String[] args) throws Exception {
        Member mbr = new MemberImpl("localhost", 9999, 0);
        ChannelData data = new ChannelData();
        data.setAddress(mbr);
        byte[] buf = new byte[8192 * 4];
        data.setMessage(new XByteBuffer(buf, false));
        buf = XByteBuffer.createDataPackage(data);
        len = buf.length;
        NioReceiver receiver = new NioReceiver();
        receiver.setPort(9999);
        receiver.setHost("localhost");
        MyList list = new MyList();
        receiver.setMessageListener(list);
        receiver.start();
        System.out.println("Listening on 9999");
        while (true) {
            try {
                synchronized (mutex) {
                    mutex.wait(5000);
                    if ( start != 0 ) {
                        System.out.println("Throughput " + df.format(mb / seconds) + " MB/seconds, messages "+count+" accepts "+accept+", total "+mb+" MB.");
                    }
                }
            }catch (Throwable x) {
                x.printStackTrace();
            }
        }
    }

    public static class MyList implements MessageListener {
        boolean first = true;


        @Override
        public void messageReceived(ChannelMessage msg) {
            if (first) {
                first = false;
                start = System.currentTimeMillis();
            }
            mb += ( (double) len) / 1024 / 1024;
            synchronized (this) {count++;}
            if ( ( (count) % 10000) == 0) {
                long time = System.currentTimeMillis();
                seconds = ( (double) (time - start)) / 1000;
                System.out.println("Throughput " + df.format(mb / seconds) + " MB/seconds, messages "+count+", total "+mb+" MB.");
            }
        }

        @Override
        public boolean accept(ChannelMessage msg) {
            synchronized (this) {accept++;}
            return true;
        }

    }
}
