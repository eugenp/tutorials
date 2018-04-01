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
package org.apache.catalina.tribes.test;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.membership.MemberImpl;
import org.apache.catalina.tribes.transport.nio.NioSender;

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
public class NioSenderTest {
    private Selector selector = null;
    private int counter = 0;
    MemberImpl mbr;
    private static int testOptions = Channel.SEND_OPTIONS_DEFAULT;
    public NioSenderTest()  {
        // Default constructor
    }

    public synchronized int inc() {
        return ++counter;
    }

    public synchronized ChannelData getMessage(Member mbr) {
        String msg = "Thread-"+Thread.currentThread().getName()+" Message:"+inc();
        ChannelData data = new ChannelData(true);
        data.setMessage(new XByteBuffer(msg.getBytes(),false));
        data.setAddress(mbr);

        return data;
    }

    public void init() throws Exception {
        synchronized (Selector.class) {
            // Selector.open() isn't thread safe
            // http://bugs.sun.com/view_bug.do?bug_id=6427854
            // Affects 1.6.0_29, fixed in 1.7.0_01
            selector = Selector.open();
        }
        mbr = new MemberImpl("localhost",4444,0);
        NioSender sender = new NioSender();
        sender.setDestination(mbr);
        sender.setDirectBuffer(true);
        sender.setSelector(selector);
        sender.setMessage(XByteBuffer.createDataPackage(getMessage(mbr)));
        sender.connect();
    }

    public void run() {
        while (true) {

            int selectedKeys = 0;
            try {
                selectedKeys = selector.select(100);
                //               if ( selectedKeys == 0 ) {
                //                   System.out.println("No registered interests. Sleeping for a second.");
                //                   Thread.sleep(100);
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
                    NioSender sender = (NioSender) sk.attachment();
                    if ( sender.process(sk, (testOptions&Channel.SEND_OPTIONS_USE_ACK)==Channel.SEND_OPTIONS_USE_ACK) ) {
                        System.out.println("Message completed for handler:"+sender);
                        Thread.sleep(2000);
                        sender.reset();
                        sender.setMessage(XByteBuffer.createDataPackage(getMessage(mbr)));
                    }


                } catch (Throwable t) {
                    t.printStackTrace();
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NioSenderTest sender = new NioSenderTest();
        sender.init();
        sender.run();
    }
}
