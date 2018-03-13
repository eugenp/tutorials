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

import java.io.Serializable;
import java.util.Random;

import org.apache.catalina.tribes.ByteMessage;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


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
public class LoadTest implements MembershipListener,ChannelListener, Runnable {
    private static final Log log = LogFactory.getLog(LoadTest.class);
    public static int size = 24000;
    public static Object mutex = new Object();
    public boolean doRun = true;

    public long bytesReceived = 0;
    public float mBytesReceived = 0;
    public int  messagesReceived = 0;
    public boolean send = true;
    public boolean debug = false;
    public int msgCount = 100;
    ManagedChannel channel=null;
    public int statsInterval = 10000;
    public long pause = 0;
    public boolean breakonChannelException = false;
    public boolean async = false;
    public long receiveStart = 0;
    public int channelOptions = Channel.SEND_OPTIONS_DEFAULT;

    static int messageSize = 0;

    public static long messagesSent = 0;
    public static long messageStartSendTime = 0;
    public static long messageEndSendTime = 0;
    public static int  threadCount = 0;

    public static synchronized void startTest() {
        threadCount++;
        if ( messageStartSendTime == 0 ) messageStartSendTime = System.currentTimeMillis();
    }

    public static synchronized void endTest() {
        threadCount--;
        if ( messageEndSendTime == 0 && threadCount==0 ) messageEndSendTime = System.currentTimeMillis();
    }


    public static synchronized long addSendStats(long count) {
        messagesSent+=count;
        return 0l;
    }

    private static void printSendStats(long counter, int messageSize) {
        float cnt = counter;
        float size = messageSize;
        float time = (System.currentTimeMillis()-messageStartSendTime) / 1000f;
        log.info("****SEND STATS-"+Thread.currentThread().getName()+"*****"+
                 "\n\tMessage count:"+counter+
                 "\n\tTotal bytes  :"+(long)(size*cnt)+
                 "\n\tTotal seconds:"+(time)+
                 "\n\tBytes/second :"+(size*cnt/time)+
                 "\n\tMBytes/second:"+(size*cnt/time/1024f/1024f));
    }


    public LoadTest(ManagedChannel channel,
                    boolean send,
                    int msgCount,
                    boolean debug,
                    long pause,
                    int stats,
                    boolean breakOnEx) {
        this.channel = channel;
        this.send = send;
        this.msgCount = msgCount;
        this.debug = debug;
        this.pause = pause;
        this.statsInterval = stats;
        this.breakonChannelException = breakOnEx;
    }


    @Override
    public void run() {

        long counter = 0;
        long total = 0;
        LoadMessage msg = new LoadMessage();

        try {
            startTest();
            while (total < msgCount) {
                if (channel.getMembers().length == 0 || (!send)) {
                    synchronized (mutex) {
                        try {
                            mutex.wait();
                        } catch (InterruptedException x) {
                            log.info("Thread interrupted from wait");
                        }
                    }
                } else {
                    try {
                        //msg.setMsgNr((int)++total);
                        counter++;
                        if (debug) {
                            printArray(msg.getMessage());
                        }
                        channel.send(channel.getMembers(), msg, channelOptions);
                        if ( pause > 0 ) {
                            if ( debug) System.out.println("Pausing sender for "+pause+" ms.");
                            Thread.sleep(pause);
                        }
                    } catch (ChannelException x) {
                        if ( debug ) log.error("Unable to send message:"+x.getMessage(),x);
                        log.error("Unable to send message:"+x.getMessage());
                        ChannelException.FaultyMember[] faulty = x.getFaultyMembers();
                        for (int i=0; i<faulty.length; i++ ) log.error("Faulty: "+faulty[i]);
                        --counter;
                        if ( this.breakonChannelException ) throw x;
                    }
                }
                if ( (counter % statsInterval) == 0 && (counter > 0)) {
                    //add to the global counter
                    counter = addSendStats(counter);
                    //print from the global counter
                    //printSendStats(LoadTest.messagesSent, LoadTest.messageSize, LoadTest.messageSendTime);
                    printSendStats(LoadTest.messagesSent, LoadTest.messageSize);

                }

            }
        }catch ( Exception x ) {
            log.error("Captured error while sending:"+x.getMessage());
            if ( debug ) log.error("",x);
            printSendStats(LoadTest.messagesSent, LoadTest.messageSize);
        }
        endTest();
    }


    /**
     * memberAdded
     *
     * @param member Member
     * TODO Implement this org.apache.catalina.tribes.MembershipListener
     *   method
     */
    @Override
    public void memberAdded(Member member) {
        log.info("Member added:"+member);
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    /**
     * memberDisappeared
     *
     * @param member Member
     * TODO Implement this org.apache.catalina.tribes.MembershipListener
     *   method
     */
    @Override
    public void memberDisappeared(Member member) {
        log.info("Member disappeared:"+member);
    }

    @Override
    public boolean accept(Serializable msg, Member mbr){
       return (msg instanceof LoadMessage) || (msg instanceof ByteMessage);
    }

    @Override
    public void messageReceived(Serializable msg, Member mbr){
        if ( receiveStart == 0 ) receiveStart = System.currentTimeMillis();
        if ( debug ) {
            if ( msg instanceof LoadMessage ) {
                printArray(((LoadMessage)msg).getMessage());
            }
        }

        if ( msg instanceof ByteMessage && !(msg instanceof LoadMessage)) {
            LoadMessage tmp = new LoadMessage();
            tmp.setMessage(((ByteMessage)msg).getMessage());
            msg = tmp;
            tmp = null;
        }


        bytesReceived+=((LoadMessage)msg).getMessage().length;
        mBytesReceived+=(((LoadMessage)msg).getMessage().length)/1024f/1024f;
        messagesReceived++;
        if ( (messagesReceived%statsInterval)==0 || (messagesReceived==msgCount)) {
            float bytes = (((LoadMessage)msg).getMessage().length*messagesReceived);
            float seconds = (System.currentTimeMillis()-receiveStart) / 1000f;
            log.info("****RECEIVE STATS-"+Thread.currentThread().getName()+"*****"+
                     "\n\tMessage count :"+(long)messagesReceived+
                     "\n\tMessage/sec   :"+messagesReceived/seconds+
                     "\n\tTotal bytes   :"+(long)bytes+
                     "\n\tTotal mbytes  :"+(long)mBytesReceived+
                     "\n\tTime since 1st:"+seconds+" seconds"+
                     "\n\tBytes/second  :"+(bytes/seconds)+
                     "\n\tMBytes/second :"+(mBytesReceived/seconds)+"\n");

        }
    }


    public static void printArray(byte[] data) {
        System.out.print("{");
        for (int i=0; i<data.length; i++ ) {
            System.out.print(data[i]);
            System.out.print(",");
        }
        System.out.println("} size:"+data.length);
    }


    public static class LoadMessage extends ByteMessage {

        public static byte[] outdata = new byte[size];
        public static Random r = new Random();
        public static int getMessageSize (LoadMessage msg) {
            return msg.getMessage().length;
        }
        static {
            r.nextBytes(outdata);
        }

        protected byte[] message = getMessage();

        public LoadMessage() {
            // Default constructor
        }

        @Override
        public byte[] getMessage() {
            if ( message == null ) {
                message = outdata;
            }
            return message;
        }

        @Override
        public void setMessage(byte[] data) {
            this.message = data;
        }
    }

    public static void usage() {
        System.out.println("Tribes Load tester.");
        System.out.println("The load tester can be used in sender or received mode or both");
        System.out.println("Usage:\n\t"+
                           "java LoadTest [options]\n\t"+
                           "Options:\n\t\t"+
                           "[-mode receive|send|both]  \n\t\t"+
                           "[-startoptions startflags (default is Channel.DEFAULT) ]  \n\t\t"+
                           "[-debug]  \n\t\t"+
                           "[-count messagecount]  \n\t\t"+
                           "[-stats statinterval]  \n\t\t"+
                           "[-pause nrofsecondstopausebetweensends]  \n\t\t"+
                           "[-threads numberofsenderthreads]  \n\t\t"+
                           "[-size messagesize]  \n\t\t"+
                           "[-sendoptions channeloptions]  \n\t\t"+
                           "[-break (halts execution on exception)]\n"+
                           "[-shutdown (issues a channel.stop() command after send is completed)]\n"+
                           "\tChannel options:"+
                           ChannelCreator.usage()+"\n\n"+
                           "Example:\n\t"+
                           "java LoadTest -port 4004\n\t"+
                           "java LoadTest -bind 192.168.0.45 -port 4005\n\t"+
                           "java LoadTest -bind 192.168.0.45 -port 4005 -mbind 192.168.0.45 -count 100 -stats 10\n");
    }

    public static void main(String[] args) throws Exception {
        boolean send = true;
        boolean debug = false;
        long pause = 0;
        int count = 1000000;
        int stats = 10000;
        boolean breakOnEx = false;
        int threads = 1;
        boolean shutdown = false;
        int startoptions = Channel.DEFAULT;
        int channelOptions = Channel.SEND_OPTIONS_DEFAULT;
        if ( args.length == 0 ) {
            args = new String[] {"-help"};
        }
        for (int i = 0; i < args.length; i++) {
            if ("-threads".equals(args[i])) {
                threads = Integer.parseInt(args[++i]);
            } else if ("-count".equals(args[i])) {
                count = Integer.parseInt(args[++i]);
                System.out.println("Sending "+count+" messages.");
            } else if ("-pause".equals(args[i])) {
                pause = Long.parseLong(args[++i])*1000;
            } else if ("-break".equals(args[i])) {
                breakOnEx = true;
            } else if ("-shutdown".equals(args[i])) {
                shutdown = true;
            } else if ("-stats".equals(args[i])) {
                stats = Integer.parseInt(args[++i]);
                System.out.println("Stats every "+stats+" message");
            } else if ("-sendoptions".equals(args[i])) {
                channelOptions = Integer.parseInt(args[++i]);
                System.out.println("Setting send options to "+channelOptions);
            } else if ("-startoptions".equals(args[i])) {
                startoptions = Integer.parseInt(args[++i]);
                System.out.println("Setting start options to "+startoptions);
            } else if ("-size".equals(args[i])) {
                size = Integer.parseInt(args[++i])-4;
                System.out.println("Message size will be:"+(size+4)+" bytes");
            } else if ("-mode".equals(args[i])) {
                if ( "receive".equals(args[++i]) ) send = false;
            } else if ("-debug".equals(args[i])) {
                debug = true;
            } else if ("-help".equals(args[i]))
            {
                usage();
                System.exit(1);
            }
        }

        ManagedChannel channel = (ManagedChannel)ChannelCreator.createChannel(args);

        LoadTest test = new LoadTest(channel,send,count,debug,pause,stats,breakOnEx);
        test.channelOptions = channelOptions;
        LoadMessage msg = new LoadMessage();

        messageSize = LoadMessage.getMessageSize(msg);
        channel.addChannelListener(test);
        channel.addMembershipListener(test);
        channel.start(startoptions);
        Runtime.getRuntime().addShutdownHook(new Shutdown(channel));
        while ( threads > 1 ) {
            Thread t = new Thread(test);
            t.setDaemon(true);
            t.start();
            threads--;
            test = new LoadTest(channel,send,count,debug,pause,stats,breakOnEx);
            test.channelOptions = channelOptions;
        }
        test.run();
        if ( shutdown && send ) channel.stop(Channel.DEFAULT);
        System.out.println("System test complete, sleeping to let threads finish.");
        Thread.sleep(60*1000*60);
    }

    public static class Shutdown extends Thread {
        ManagedChannel channel = null;
        public Shutdown(ManagedChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            System.out.println("Shutting down...");
            SystemExit exit = new SystemExit(5000);
            exit.setDaemon(true);
            exit.start();
            try {
                channel.stop(Channel.DEFAULT);

            }catch ( Exception x ) {
                x.printStackTrace();
            }
            System.out.println("Channel stopped.");
        }
    }
    public static class SystemExit extends Thread {
        private long delay;
        public SystemExit(long delay) {
            this.delay = delay;
        }
        @Override
        public void run () {
            try {
                Thread.sleep(delay);
            }catch ( Exception x ) {
                x.printStackTrace();
            }
            System.exit(0);

        }
    }

}