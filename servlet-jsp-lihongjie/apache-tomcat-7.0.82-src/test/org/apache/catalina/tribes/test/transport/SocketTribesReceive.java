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

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

import org.apache.catalina.tribes.io.XByteBuffer;

public class SocketTribesReceive {
    static long start = 0;
    static double mb = 0;
    //static byte[] buf = new byte[32871];
    static byte[] buf = new byte[32871];
    static boolean first = true;
    static int count = 0;
    static DecimalFormat df = new DecimalFormat("##.00");
    static BigDecimal total = new BigDecimal((double)0);
    static BigDecimal bytes = new BigDecimal((double)32871);


    public static void main(String[] args) throws Exception {
        int size = 43800;
        if (args.length > 0 ) try {size=Integer.parseInt(args[0]);}catch(Exception x){ /* Ignore */ }
        XByteBuffer xbuf = new XByteBuffer(43800,true);
        ServerSocket srvSocket = new ServerSocket(9999);
        System.out.println("Listening on 9999");
        Socket socket = srvSocket.accept();
        socket.setReceiveBufferSize(size);
        InputStream in = socket.getInputStream();
        Thread t = new Thread() {
            @Override
            public void run() {
                while ( true ) {
                    try {
                        Thread.sleep(1000);
                        printStats(start, mb, count, df, total);
                    }catch ( Exception x ) { /* Ignore */ }
                }
            }
        };
        t.setDaemon(true);
        t.start();

        while ( true ) {
            if ( first ) { first = false; start = System.currentTimeMillis();}
            int len = in.read(buf);
            if ( len == -1 ) {
                printStats(start, mb, count, df, total);
                System.exit(1);
            }
            xbuf.append(buf,0,len);
            if ( bytes.intValue() != len ) bytes = new BigDecimal((double)len);
            total = total.add(bytes);
            while ( xbuf.countPackages(true) > 0 ) {
                xbuf.extractPackage(true);
                count++;
            }
            mb += ( (double) len) / 1024 / 1024;
            if ( ((count) % 10000) == 0 ) {
                printStats(start, mb, count, df, total);
            }
        }

    }

    private static void printStats(long start, double mb, int count, DecimalFormat df, BigDecimal total) {
        long time = System.currentTimeMillis();
        double seconds = ((double)(time-start))/1000;
        System.out.println("Throughput "+df.format(mb/seconds)+" MB/seconds messages "+count+", total "+mb+" MB, total "+total+" bytes.");
    }
}