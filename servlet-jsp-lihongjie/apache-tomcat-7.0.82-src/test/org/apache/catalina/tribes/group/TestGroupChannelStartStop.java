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
package org.apache.catalina.tribes.group;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.transport.ReceiverBase;

/**
 * @author Filip Hanik
 * @version 1.0
 */
public class TestGroupChannelStartStop {
    private GroupChannel channel = null;
    private int udpPort = 45543;

    @Before
    public void setUp() throws Exception {
        channel = new GroupChannel();
    }

    @After
    public void tearDown() throws Exception {
        try {
            channel.stop(Channel.DEFAULT);
        } catch (Exception ignore) {
            // Ignore
        }
    }

    @Test
    public void testDoubleFullStart() throws Exception {
        int count = 0;
        try {
            channel.start(Channel.DEFAULT);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(Channel.DEFAULT);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        assertEquals(count,2);
        channel.stop(Channel.DEFAULT);
    }

    @Test
    public void testScrap() throws Exception {
        System.out.println(channel.getChannelReceiver().getClass());
        ((ReceiverBase)channel.getChannelReceiver()).setMaxThreads(1);
    }

    @Test
    public void testDoublePartialStart() throws Exception {
        //try to double start the RX
        int count = 0;
        try {
            channel.start(Channel.SND_RX_SEQ);
            channel.start(Channel.MBR_RX_SEQ);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(Channel.MBR_RX_SEQ);
            count++;
        } catch ( Exception x){
            // expected
        }
        assertEquals(count,1);
        channel.stop(Channel.DEFAULT);
        //double the membership sender
        count = 0;
        try {
            channel.start(Channel.SND_RX_SEQ);
            channel.start(Channel.MBR_TX_SEQ);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(Channel.MBR_TX_SEQ);
            count++;
        } catch ( Exception x){
            // expected
        }
        assertEquals(count,1);
        channel.stop(Channel.DEFAULT);

        count = 0;
        try {
            channel.start(Channel.SND_RX_SEQ);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(Channel.SND_RX_SEQ);
            count++;
        } catch ( Exception x){
            // expected
        }
        assertEquals(count,1);
        channel.stop(Channel.DEFAULT);

        count = 0;
        try {
            channel.start(Channel.SND_TX_SEQ);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(Channel.SND_TX_SEQ);
            count++;
        } catch ( Exception x){
            // expected
        }
        assertEquals(count,1);
        channel.stop(Channel.DEFAULT);
    }

    @Test
    public void testFalseOption() throws Exception {
        int flag = 0xFFF0;//should get ignored by the underlying components
        int count = 0;
        try {
            channel.start(flag);
            count++;
        } catch ( Exception x){x.printStackTrace();}
        try {
            channel.start(flag);
            count++;
        } catch ( Exception x){
            // expected
        }
        assertEquals(count,2);
        channel.stop(Channel.DEFAULT);
    }

    @Test
    public void testUdpReceiverStart() throws Exception {
        ReceiverBase rb = (ReceiverBase)channel.getChannelReceiver();
        rb.setUdpPort(udpPort);
        channel.start(Channel.DEFAULT);
        Thread.sleep(1000);
        channel.stop(Channel.DEFAULT);
    }
}
