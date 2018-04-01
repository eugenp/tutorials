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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor;
import org.apache.catalina.tribes.group.interceptors.NonBlockingCoordinator;
import org.apache.catalina.tribes.group.interceptors.TcpFailureDetector;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.util.Arrays;


public class CoordinationDemo {
    static int CHANNEL_COUNT = 5;
    static int SCREEN_WIDTH = 120;
    static long SLEEP_TIME = 10;
    static int CLEAR_SCREEN = 30;
    static boolean MULTI_THREAD = false;
    static boolean[] VIEW_EVENTS = new boolean[255];
    StringBuilder statusLine = new StringBuilder();
    Status[] status = null;
    BufferedReader reader = null;
    /**
     * Construct and show the application.
     */
    public CoordinationDemo() {
        // Default constructor
    }

    public void init() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        status = new Status[CHANNEL_COUNT];
    }


    public void clearScreen() {
        StringBuilder buf = new StringBuilder(700);
        for (int i=0; i<CLEAR_SCREEN; i++ ) buf.append("\n");
        System.out.println(buf);
    }

    public void printMenuOptions() {
        System.out.println("Commands:");
        System.out.println("\tstart [member id]");
        System.out.println("\tstop  [member id]");
        System.out.println("\tprint (refresh)");
        System.out.println("\tquit");
        System.out.print("Enter command:");
    }

    public synchronized void printScreen() {
        clearScreen();
        System.out.println(" ###."+getHeader());
        for ( int i=0; i<status.length; i++ ) {
            System.out.print(leftfill(String.valueOf(i+1)+".",5," "));
            if ( status[i] != null ) System.out.print(status[i].getStatusLine());
        }
        System.out.println("\n\n");
        System.out.println("Overall status:"+statusLine);
        printMenuOptions();

    }

    public String getHeader() {
        //member - 30
        //running- 10
        //coord - 30
        //view-id - 24
        //view count - 8

        StringBuilder buf = new StringBuilder();
        buf.append(leftfill("Member",30," "));
        buf.append(leftfill("Running",10," "));
        buf.append(leftfill("Coord",30," "));
        buf.append(leftfill("View-id(short)",24," "));
        buf.append(leftfill("Count",8," "));
        buf.append("\n");

        buf.append(rightfill("==="+new java.sql.Timestamp(System.currentTimeMillis()).toString(),SCREEN_WIDTH,"="));
        buf.append("\n");
        return buf.toString();
    }

    public String[] tokenize(String line) {
        StringTokenizer tz = new StringTokenizer(line," ");
        String[] result = new String[tz.countTokens()];
        for (int i=0; i<result.length; i++ ) result[i] = tz.nextToken();
        return result;
    }

    public void waitForInput() throws IOException {
        for ( int i=0; i<status.length; i++ ) status[i] = new Status(this);
        printScreen();
        String l = reader.readLine();
        String[] args;
        if (l == null) {
            args = new String[] {};
        } else {
            args = tokenize(l);
        }
        while ( args.length >= 1 && (!"quit".equalsIgnoreCase(args[0]))) {
            if ("start".equalsIgnoreCase(args[0])) {
                cmdStart(args);
            } else if ("stop".equalsIgnoreCase(args[0])) {
                cmdStop(args);

            }
            printScreen();
            l = reader.readLine();
            if (l != null) {
                args = tokenize(l);
            }
        }
        for ( int i=0; i<status.length; i++ ) status[i].stop();
    }

    private void cmdStop(String[] args) {
        if ( args.length == 1 ) {
            setSystemStatus("System shutting down...");
            Thread[] t = new Thread[CHANNEL_COUNT];
            for (int i = 0; i < status.length; i++) {
                final int j = i;
                t[j] = new Thread() {
                    @Override
                    public void run() {
                        status[j].stop();
                    }
                };
            }
            for (int i = 0; i < status.length; i++) if (MULTI_THREAD ) t[i].start(); else t[i].run();
            setSystemStatus("System stopped.");
        } else {
            int index = -1;
            try { index = Integer.parseInt(args[1])-1;}catch ( Exception x ) {setSystemStatus("Invalid index:"+args[1]);}
            if ( index >= 0 ) {
                setSystemStatus("Stopping member:"+(index+1));
                status[index].stop();
                setSystemStatus("Member stopped:"+(index+1));
            }
        }
    }

    private void cmdStart(String[] args) {
        if ( args.length == 1 ) {
            setSystemStatus("System starting up...");
            Thread[] t = new Thread[CHANNEL_COUNT];
            for (int i = 0; i < status.length; i++) {
                final int j = i;
                t[j] = new Thread() {
                    @Override
                    public void run() {
                        status[j].start();
                    }
                };
            }
            for (int i = 0; i < status.length; i++) if (MULTI_THREAD ) t[i].start(); else t[i].run();
            setSystemStatus("System started.");
        } else {
            int index = -1;
            try { index = Integer.parseInt(args[1])-1;}catch ( Exception x ) {setSystemStatus("Invalid index:"+args[1]);}
            if ( index >= 0 ) {
                setSystemStatus("Starting member:"+(index+1));
                status[index].start();
                setSystemStatus("Member started:"+(index+1));
            }
        }
    }

    public void setSystemStatus(String status) {
        statusLine.delete(0,statusLine.length());
        statusLine.append(status);
    }


    public static void setEvents(String events) {
        java.util.Arrays.fill(VIEW_EVENTS,false);
        StringTokenizer t = new StringTokenizer(events,",");
        while (t.hasMoreTokens() ) {
            int idx = Integer.parseInt(t.nextToken());
            VIEW_EVENTS[idx] = true;
        }
    }

    public static void run(String[] args,CoordinationDemo demo) throws Exception {
        usage();
        java.util.Arrays.fill(VIEW_EVENTS,true);

        for (int i=0; i<args.length; i++ ) {
            if ( "-c".equals(args[i]) )
                CHANNEL_COUNT = Integer.parseInt(args[++i]);
            else if ( "-t".equals(args[i]) )
                MULTI_THREAD = Boolean.parseBoolean(args[++i]);
            else if ( "-s".equals(args[i]) )
                SLEEP_TIME = Long.parseLong(args[++i]);
            else if ( "-sc".equals(args[i]) )
                CLEAR_SCREEN = Integer.parseInt(args[++i]);
            else if ( "-p".equals(args[i]) )
                setEvents(args[++i]);
            else if ( "-h".equals(args[i]) ) System.exit(0);
        }
        demo.init();
        demo.waitForInput();
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("\tjava org.apache.catalina.tribes.demos.CoordinationDemo -c channel-count(int) -t multi-thread(true|false) -s sleep-time(ms) -sc clear-screen(int) -p view_events_csv(1,2,5,7)");
        System.out.println("Example:");
        System.out.println("\tjava o.a.c.t.d.CoordinationDemo -> starts demo single threaded start/stop with 5 channels");
        System.out.println("\tjava o.a.c.t.d.CoordinationDemo -c 10 -> starts demo single threaded start/stop with 10 channels");
        System.out.println("\tjava o.a.c.t.d.CoordinationDemo -c 7 -t true -s 1000 -sc 50-> starts demo multi threaded start/stop with 7 channels and 1 second sleep time between events and 50 lines to clear screen");
        System.out.println("\tjava o.a.c.t.d.CoordinationDemo -t true -p 12 -> starts demo multi threaded start/stop with 5 channels and only prints the EVT_CONF_RX event");
        System.out.println();
    }
    public static void main(String[] args) throws Exception {
        CoordinationDemo demo = new CoordinationDemo();
        run(args,demo);
    }

    public static String leftfill(String value, int length, String ch) {
        return fill(value,length,ch,true);
    }

    public static String rightfill(String value, int length, String ch) {
        return fill(value,length,ch,false);
    }

    public static String fill(String value, int length, String ch, boolean left) {
        StringBuilder buf = new StringBuilder();
        if ( !left ) buf.append(value.trim());
        for (int i=value.trim().length(); i<length; i++ ) buf.append(ch);
        if ( left ) buf.append(value.trim());
        return buf.toString();
    }


    public static class Status {
        public CoordinationDemo parent;
        public GroupChannel channel;
        NonBlockingCoordinator interceptor = null;
        public String status;
        public Exception error;
        public String startstatus = "new";

        public Status(CoordinationDemo parent) {
            this.parent = parent;
        }

        public String getStatusLine() {
            //member - 30
            //running- 10
            //coord - 30
            //view-id - 24
            //view count - 8
            StringBuilder buf = new StringBuilder();
            String local = "";
            String coord = "";
            String viewId = "";
            String count = "0";
            if ( channel != null ) {
                Member lm = channel.getLocalMember(false);
                local = lm!=null?lm.getName():"";
                coord = interceptor!=null && interceptor.getCoordinator()!=null?interceptor.getCoordinator().getName():"";
                if (interceptor != null) {
                    viewId = getByteString(interceptor.getViewId()!=null?interceptor.getViewId().getBytes():new byte[0]);
                    count = String.valueOf(interceptor.getView().length);
                }
            }
            buf.append(leftfill(local,30," "));
            buf.append(leftfill(startstatus, 10, " "));
            buf.append(leftfill(coord, 30, " "));
            buf.append(leftfill(viewId, 24, " "));
            buf.append(leftfill(count, 8, " "));
            buf.append("\n");
            buf.append("Status:"+status);
            buf.append("\n");
            return buf.toString();
        }

        public String getByteString(byte[] b) {
            if ( b == null ) return "{}";
            return Arrays.toString(b,0,Math.min(b.length,4));
        }

        public void start() {
            try {
                if ( channel == null ) {
                    channel = createChannel();
                    startstatus = "starting";
                    channel.start(Channel.DEFAULT);
                    startstatus = "running";
                } else {
                    status = "Channel already started.";
                }
            } catch ( Exception x ) {
                synchronized (System.err) {
                    System.err.println("Start failed:");
                    StackTraceElement[] els = x.getStackTrace();
                    for (int i = 0; i < els.length; i++) System.err.println(els[i].toString());
                }
                status = "Start failed:"+x.getMessage();
                error = x;
                startstatus = "failed";
                try { channel.stop(Channel.DEFAULT);}catch(Exception ignore){
                    // Ignore
                }
                channel = null;
                interceptor = null;
            }
        }

        public void stop() {
            try {
                if ( channel != null ) {
                    channel.stop(Channel.DEFAULT);
                    status = "Channel Stopped";
                } else {
                    status = "Channel Already Stopped";
                }
            }catch ( Exception x )  {
                synchronized (System.err) {
                    System.err.println("Stop failed:");
                    StackTraceElement[] els = x.getStackTrace();
                    for (int i = 0; i < els.length; i++) System.err.println(els[i].toString());
                }

                status = "Stop failed:"+x.getMessage();
                error = x;
            }finally {
                startstatus = "stopped";
                channel = null;
                interceptor = null;
            }
        }

        public GroupChannel createChannel() {
            channel = new GroupChannel();
            ((ReceiverBase)channel.getChannelReceiver()).setAutoBind(100);
            interceptor = new NonBlockingCoordinator() {
                @Override
                public void fireInterceptorEvent(InterceptorEvent event) {
                    status = event.getEventTypeDesc();
                    int type = event.getEventType();
                    boolean display = VIEW_EVENTS[type];
                    if ( display ) parent.printScreen();
                    try { Thread.sleep(SLEEP_TIME); }catch ( Exception x){
                        // Ignore
                    }
                }
            };
            channel.addInterceptor(interceptor);
            channel.addInterceptor(new TcpFailureDetector());
            channel.addInterceptor(new MessageDispatch15Interceptor());
            return channel;
        }
    }
}