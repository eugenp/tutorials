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
package org.apache.catalina.tribes.demos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.catalina.tribes.util.UUIDGenerator;

public class MembersWithProperties implements MembershipListener{
    static Thread main;

    public MembersWithProperties(Channel channel, Properties props) throws IOException {
        channel.addMembershipListener(this);
        ManagedChannel mchannel = (ManagedChannel)channel;
        mchannel.getMembershipService().setPayload(getPayload(props));
    }

    byte[] getPayload(Properties props) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        props.store(bout,"");
        return bout.toByteArray();
    }

    Properties getProperties(byte[] payload) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(payload);
        Properties props = new Properties();
        props.load(bin);
        return props;
    }

    @Override
    public void memberAdded(Member member) {
        try {
            System.out.println("Received member added:"+member);
            System.out.println("Payload["+member+"] :");
            getProperties(member.getPayload()).store(System.out,"");
        }catch ( Exception x ) {
            x.printStackTrace();
        }
    }

    @Override
    public void memberDisappeared(Member member) {
        try {
            System.out.println("Received member disappeared:"+member);
            System.out.println("Payload["+member+"] :");
            getProperties(member.getPayload()).store(System.out,"");
        }catch ( Exception x ) {
            x.printStackTrace();
        }
    }

    public static void usage() {
        System.out.println("Tribes Member Properties demo.");
        System.out.println("Usage:\n\t" +
                           "java MemberWithProperties \n\t" +
                           "Channel options:" +
                           ChannelCreator.usage() + "\n\n" +
                           "Example:\n\t" +
                           "java MembersWithProperties -port 4004\n\t" +
                           "java MembersWithProperties -bind 192.168.0.45 -port 4005\n\t" +
                           "java MembersWithProperties -bind 192.168.0.45 -port 4005 -mbind 192.168.0.45 -count 100 -stats 10\n");
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        if (args.length==0) usage();
        main = Thread.currentThread();
        ManagedChannel channel = (ManagedChannel) ChannelCreator.createChannel(args);
        Properties props = new Properties();
        props.setProperty("mydomainkey","mydomainvalue");
        props.setProperty("someotherkey", Arrays.toString(UUIDGenerator.randomUUID(true)));
        new MembersWithProperties(channel, props);
        channel.start(Channel.DEFAULT);
        Runtime.getRuntime().addShutdownHook(new Shutdown(channel));
        try {
            Thread.sleep(Long.MAX_VALUE);
        }catch(InterruptedException ix) {
            Thread.sleep(5000);//allow everything to shutdown
        }
    }

    public static class Shutdown extends Thread {
        ManagedChannel channel = null;
        public Shutdown(ManagedChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            System.out.println("Shutting down...");
            try {
                channel.stop(Channel.DEFAULT);
            } catch (Exception x) {
                x.printStackTrace();
            }
            System.out.println("Channel stopped.");
            main.interrupt();
        }
    }
}