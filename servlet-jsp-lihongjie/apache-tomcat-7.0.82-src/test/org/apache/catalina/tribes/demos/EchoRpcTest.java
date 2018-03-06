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

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.Response;
import org.apache.catalina.tribes.group.RpcCallback;
import org.apache.catalina.tribes.group.RpcChannel;


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
public class EchoRpcTest implements RpcCallback, Runnable {

    Channel channel;
    int count;
    String message;
    long pause;
    RpcChannel rpc;
    int options;
    long timeout;
    String name;

    public EchoRpcTest(Channel channel, String name, int count, String message, long pause, int options, long timeout) {
        this.channel = channel;
        this.count = count;
        this.message = message;
        this.pause = pause;
        this.options = options;
        this.rpc = new RpcChannel(name.getBytes(),channel,this);
        this.timeout = timeout;
        this.name = name;
    }

    /**
     * If the reply has already been sent to the requesting thread, the rpc
     * callback can handle any data that comes in after the fact.
     *
     * @param msg Serializable
     * @param sender Member
     * TODO Implement this org.apache.catalina.tribes.tipis.RpcCallback
     *   method
     */
    @Override
    public void leftOver(Serializable msg, Member sender) {
        System.out.println("Received a left over message from ["+sender.getName()+"] with data ["+msg+"]");
    }

    /**
     *
     * @param msg Serializable
     * @param sender Member
     * @return Serializable - null if no reply should be sent
     * TODO Implement this org.apache.catalina.tribes.tipis.RpcCallback
     *   method
     */
    @Override
    public Serializable replyRequest(Serializable msg, Member sender) {
        System.out.println("Received a reply request message from ["+sender.getName()+"] with data ["+msg+"]");
        return "Reply("+name+"):"+msg;
    }

    @Override
    public void run() {
        long counter = 0;
        while (counter<count) {
            String msg = message + " cnt="+(++counter);
            try {
                System.out.println("Sending ["+msg+"]");
                long start = System.currentTimeMillis();
                Response[] resp = rpc.send(channel.getMembers(),msg,options,Channel.SEND_OPTIONS_DEFAULT,timeout);
                System.out.println("Send of ["+msg+"] completed. Nr of responses="+resp.length+" Time:"+(System.currentTimeMillis()-start)+" ms.");
                for ( int i=0; i<resp.length; i++ ) {
                    System.out.println("Received a response message from ["+resp[i].getSource().getName()+"] with data ["+resp[i].getMessage()+"]");
                }
                Thread.sleep(pause);
            }catch(Exception x){
                // Ignore
            }
        }
    }

    public static void usage() {
            System.out.println("Tribes RPC tester.");
            System.out.println("Usage:\n\t"+
                               "java EchoRpcTest [options]\n\t"+
                               "Options:\n\t\t"+
                               "[-mode all|first|majority]  \n\t\t"+
                               "[-debug]  \n\t\t"+
                               "[-count messagecount]  \n\t\t"+
                               "[-timeout timeoutinms]  \n\t\t"+
                               "[-stats statinterval]  \n\t\t"+
                               "[-pause nrofsecondstopausebetweensends]  \n\t\t"+
                               "[-message message]  \n\t\t"+
                               "[-name rpcname]  \n\t\t"+
                               "[-break (halts execution on exception)]\n"+
                               "\tChannel options:"+
                               ChannelCreator.usage()+"\n\n"+
                               "Example:\n\t"+
                               "java EchoRpcTest -port 4004\n\t"+
                               "java EchoRpcTest -bind 192.168.0.45 -port 4005\n\t"+
                               "java EchoRpcTest -bind 192.168.0.45 -port 4005 -mbind 192.168.0.45 -count 100 -stats 10\n");
        }

        public static void main(String[] args) throws Exception {
            long pause = 3000;
            int count = 1000000;
            int stats = 10000;
            String name = "EchoRpcId";
            int options = RpcChannel.ALL_REPLY;
            long timeout = 15000;
            String message = "EchoRpcMessage";
            if (args.length == 0) {
                usage();
                System.exit(1);
            }
            for (int i = 0; i < args.length; i++) {
                if ("-threads".equals(args[i])) {
                    // Not used
                } else if ("-count".equals(args[i])) {
                    count = Integer.parseInt(args[++i]);
                    System.out.println("Sending "+count+" messages.");
                } else if ("-pause".equals(args[i])) {
                    pause = Long.parseLong(args[++i])*1000;
                } else if ("-break".equals(args[i])) {
                    // Not used
                } else if ("-stats".equals(args[i])) {
                    stats = Integer.parseInt(args[++i]);
                    System.out.println("Stats every "+stats+" message");
                } else if ("-timeout".equals(args[i])) {
                    timeout = Long.parseLong(args[++i]);
                } else if ("-message".equals(args[i])) {
                    message = args[++i];
                } else if ("-name".equals(args[i])) {
                    name = args[++i];
                } else if ("-mode".equals(args[i])) {
                    if ( "all".equals(args[++i]) ) options = RpcChannel.ALL_REPLY;
                    else if ( "first".equals(args[i]) ) options = RpcChannel.FIRST_REPLY;
                    else if ( "majority".equals(args[i]) ) options = RpcChannel.MAJORITY_REPLY;
                } else if ("-debug".equals(args[i])) {
                    // Not used
                } else if ("-help".equals(args[i])) {
                    usage();
                    System.exit(1);
                }
            }


            ManagedChannel channel = (ManagedChannel)ChannelCreator.createChannel(args);
            EchoRpcTest test = new EchoRpcTest(channel,name,count,message,pause,options,timeout);
            channel.start(Channel.DEFAULT);
            Runtime.getRuntime().addShutdownHook(new Shutdown(channel));
            test.run();

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
    }}