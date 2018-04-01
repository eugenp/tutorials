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
package org.apache.catalina.tribes.test.channel;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.group.GroupChannel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TestRemoteProcessException {
    private int msgCount = 10000;
    private GroupChannel channel1;
    private GroupChannel channel2;
    private Listener listener1;

    @Before
    public void setUp() throws Exception {
        channel1 = new GroupChannel();
        channel2 = new GroupChannel();
        listener1 = new Listener();
        channel2.addChannelListener(listener1);
        TesterUtil.addRandomDomain(new ManagedChannel[] {channel1, channel2});
        channel1.start(Channel.DEFAULT);
        channel2.start(Channel.DEFAULT);
    }

    @After
    public void tearDown() throws Exception {
        channel1.stop(Channel.DEFAULT);
        channel2.stop(Channel.DEFAULT);
    }

    @Test
    public void testDataSendSYNCACK() throws Exception {
        System.err.println("Starting SYNC_ACK");
        int errC=0, nerrC=0;
        for (int i=0; i<msgCount; i++) {
            boolean error = Data.r.nextBoolean();
            try {
                channel1.send(channel1.getMembers(),
                        Data.createRandomData(error),
                        Channel.SEND_OPTIONS_SYNCHRONIZED_ACK
                                | Channel.SEND_OPTIONS_USE_ACK);
                if (error) {
                    fail("A ChannelException was expected");
                }
            } catch (ChannelException e) {
                if (!error) {
                    throw e;
                }
            }
            if ( error ) errC++; else nerrC++;
        }
        System.err.println("Finished SYNC_ACK");

        // The listener sees 3 copies of the first "error" message,
        // as it is being re-sent. Thus the listener1 count is off by +2.
        final int duplicate = 2;

        assertEquals("Checking failure messages.", errC + duplicate,
                listener1.errCnt);
        assertEquals("Checking success messages.", nerrC, listener1.noErrCnt);
        assertEquals("Checking all messages.", msgCount + duplicate,
                listener1.noErrCnt + listener1.errCnt);
        System.out.println("Listener 1 stats:");
        listener1.printStats(System.out);
    }

    public static class Listener implements ChannelListener {
        long noErrCnt = 0;
        long errCnt = 0;
        @Override
        public boolean accept(Serializable s, Member m) {
            return (s instanceof Data);
        }

        @Override
        public void messageReceived(Serializable s, Member m) {
            Data d = (Data)s;
            if ( !Data.verify(d) ) {
                System.err.println("ERROR");
            } else {
                if (d.error) {
                    errCnt++;
                    if ( (errCnt % 100) == 0) {
                        printStats(System.err);
                    }
                    throw new IllegalArgumentException();
                }
                noErrCnt++;
                if ( (noErrCnt % 100) == 0) {
                    printStats(System.err);
                }
            }
        }

        public void printStats(PrintStream stream) {
            stream.println("NORMAL:" + noErrCnt);
            stream.println("FAILURES:" + errCnt);
            stream.println("TOTAL:" + (errCnt+noErrCnt));
        }
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = 1L;
        public int length;
        public byte[] data;
        public byte key;
        public boolean error = false;
        public static Random r = new Random();
        public static Data createRandomData(boolean error) {
            int i = r.nextInt();
            i = ( i % 127 );
            int length = Math.abs(r.nextInt() % 65555);
            Data d = new Data();
            d.length = length;
            d.key = (byte)i;
            d.data = new byte[length];
            Arrays.fill(d.data,d.key);
            d.error = error;
            return d;
        }

        public static boolean verify(Data d) {
            boolean result = (d.length == d.data.length);
            for ( int i=0; result && (i<d.data.length); i++ ) result = result && d.data[i] == d.key;
            return result;
        }
    }



}
