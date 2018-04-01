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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.DomainFilterInterceptor;
import org.apache.catalina.tribes.group.interceptors.FragmentationInterceptor;
import org.apache.catalina.tribes.group.interceptors.GzipInterceptor;
import org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor;
import org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor;
import org.apache.catalina.tribes.group.interceptors.OrderInterceptor;
import org.apache.catalina.tribes.group.interceptors.StaticMembershipInterceptor;
import org.apache.catalina.tribes.group.interceptors.TcpFailureDetector;
import org.apache.catalina.tribes.group.interceptors.ThroughputInterceptor;
import org.apache.catalina.tribes.membership.McastService;
import org.apache.catalina.tribes.membership.MemberImpl;
import org.apache.catalina.tribes.transport.MultiPointSender;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 *
 * <p>Company: </p>
 *
 * @author fhanik
 * @version 1.0
 */
public class ChannelCreator {


    public static StringBuilder usage() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n\t\t[-bind tcpbindaddress]")
           .append("\n\t\t[-tcpselto tcpselectortimeout]")
           .append("\n\t\t[-tcpthreads tcpthreadcount]")
           .append("\n\t\t[-port tcplistenport]")
           .append("\n\t\t[-autobind tcpbindtryrange]")
           .append("\n\t\t[-ackto acktimeout]")
           .append("\n\t\t[-receiver org.apache.catalina.tribes.transport.nio.NioReceiver|org.apache.catalina.tribes.transport.bio.BioReceiver|]")
           .append("\n\t\t[-transport org.apache.catalina.tribes.transport.nio.PooledParallelSender|org.apache.catalina.tribes.transport.bio.PooledMultiSender]")
           .append("\n\t\t[-transport.xxx transport specific property]")
           .append("\n\t\t[-maddr multicastaddr]")
           .append("\n\t\t[-mport multicastport]")
           .append("\n\t\t[-mbind multicastbindaddr]")
           .append("\n\t\t[-mfreq multicastfrequency]")
           .append("\n\t\t[-mdrop multicastdroptime]")
           .append("\n\t\t[-gzip]")
           .append("\n\t\t[-static hostname:port (-static localhost:9999 -static 127.0.0.1:8888 can be repeated)]")
           .append("\n\t\t[-order]")
           .append("\n\t\t[-ordersize maxorderqueuesize]")
           .append("\n\t\t[-frag]")
           .append("\n\t\t[-fragsize maxmsgsize]")
           .append("\n\t\t[-throughput]")
           .append("\n\t\t[-failuredetect]")
           .append("\n\t\t[-async]")
           .append("\n\t\t[-asyncsize maxqueuesizeinkilobytes]");
       return buf;

    }

    public static Channel createChannel(String[] args) throws Exception {
        String bind = "auto";
        int port = 4001;
        String mbind = null;
        boolean gzip = false;
        int tcpseltimeout = 5000;
        int tcpthreadcount = 4;
        int acktimeout = 15000;
        String mcastaddr = "228.0.0.5";
        int mcastport = 45565;
        long mcastfreq = 500;
        long mcastdrop = 2000;
        boolean order = false;
        int ordersize = Integer.MAX_VALUE;
        boolean frag = false;
        int fragsize = 1024;
        int autoBind = 10;
        ArrayList<Member> staticMembers = new ArrayList<Member>();
        Properties transportProperties = new Properties();
        String transport = "org.apache.catalina.tribes.transport.nio.PooledParallelSender";
        String receiver = "org.apache.catalina.tribes.transport.nio.NioReceiver";
        boolean async = false;
        int asyncsize = 1024*1024*50; //50MB
        boolean throughput = false;
        boolean failuredetect = false;

        for (int i = 0; i < args.length; i++) {
            if ("-bind".equals(args[i])) {
                bind = args[++i];
            } else if ("-port".equals(args[i])) {
                port = Integer.parseInt(args[++i]);
            } else if ("-autobind".equals(args[i])) {
                autoBind = Integer.parseInt(args[++i]);
            } else if ("-tcpselto".equals(args[i])) {
                tcpseltimeout = Integer.parseInt(args[++i]);
            } else if ("-tcpthreads".equals(args[i])) {
                tcpthreadcount = Integer.parseInt(args[++i]);
            } else if ("-gzip".equals(args[i])) {
                gzip = true;
            } else if ("-async".equals(args[i])) {
                async = true;
            } else if ("-failuredetect".equals(args[i])) {
                failuredetect = true;
            } else if ("-asyncsize".equals(args[i])) {
                asyncsize = Integer.parseInt(args[++i]);
                System.out.println("Setting MessageDispatchInterceptor.maxQueueSize="+asyncsize);
            } else if ("-static".equals(args[i])) {
                String d = args[++i];
                String h = d.substring(0,d.indexOf(':'));
                String p = d.substring(h.length()+1);
                MemberImpl m = new MemberImpl(h,Integer.parseInt(p),2000);
                staticMembers.add(m);
            } else if ("-throughput".equals(args[i])) {
                throughput = true;
            } else if ("-order".equals(args[i])) {
                order = true;
            } else if ("-ordersize".equals(args[i])) {
                ordersize = Integer.parseInt(args[++i]);
                System.out.println("Setting OrderInterceptor.maxQueue="+ordersize);
            } else if ("-frag".equals(args[i])) {
                frag = true;
            } else if ("-fragsize".equals(args[i])) {
                fragsize = Integer.parseInt(args[++i]);
                System.out.println("Setting FragmentationInterceptor.maxSize="+fragsize);
            } else if ("-ackto".equals(args[i])) {
                acktimeout = Integer.parseInt(args[++i]);
            } else if ("-transport".equals(args[i])) {
                transport = args[++i];
            } else if (args[i]!=null && args[i].startsWith("transport.")) {
                String key = args[i];
                String val = args[++i];
                transportProperties.setProperty(key,val);
            } else if ("-receiver".equals(args[i])) {
                receiver = args[++i];
            } else if ("-maddr".equals(args[i])) {
                mcastaddr = args[++i];
            } else if ("-mport".equals(args[i])) {
                mcastport = Integer.parseInt(args[++i]);
            } else if ("-mfreq".equals(args[i])) {
                mcastfreq = Long.parseLong(args[++i]);
            } else if ("-mdrop".equals(args[i])) {
                mcastdrop = Long.parseLong(args[++i]);
            } else if ("-mbind".equals(args[i])) {
                mbind = args[++i];
            }
        }

        System.out.println("Creating receiver class="+receiver);
        Class<?> cl = Class.forName(receiver, true,
                ChannelCreator.class.getClassLoader());
        ReceiverBase rx = (ReceiverBase)cl.newInstance();
        rx.setAddress(bind);
        rx.setPort(port);
        rx.setSelectorTimeout(tcpseltimeout);
        rx.setMaxThreads(tcpthreadcount);
        rx.setMinThreads(tcpthreadcount);
        rx.getBind();
        rx.setRxBufSize(43800);
        rx.setTxBufSize(25188);
        rx.setAutoBind(autoBind);


        ReplicationTransmitter ps = new ReplicationTransmitter();
        System.out.println("Creating transport class="+transport);
        MultiPointSender sender = (MultiPointSender)Class.forName(transport,true,ChannelCreator.class.getClassLoader()).newInstance();
        sender.setTimeout(acktimeout);
        sender.setMaxRetryAttempts(2);
        sender.setRxBufSize(43800);
        sender.setTxBufSize(25188);

        Iterator<Object> i = transportProperties.keySet().iterator();
        while ( i.hasNext() ) {
            String key = (String)i.next();
            IntrospectionUtils.setProperty(sender,key,transportProperties.getProperty(key));
        }
        ps.setTransport(sender);

        McastService service = new McastService();
        service.setAddress(mcastaddr);
        if (mbind != null) service.setMcastBindAddress(mbind);
        service.setFrequency(mcastfreq);
        service.setMcastDropTime(mcastdrop);
        service.setPort(mcastport);

        ManagedChannel channel = new GroupChannel();
        channel.setChannelReceiver(rx);
        channel.setChannelSender(ps);
        channel.setMembershipService(service);

        if ( throughput ) channel.addInterceptor(new ThroughputInterceptor());
        if (gzip) channel.addInterceptor(new GzipInterceptor());
        if ( frag ) {
            FragmentationInterceptor fi = new FragmentationInterceptor();
            fi.setMaxSize(fragsize);
            channel.addInterceptor(fi);
        }
        if (order) {
            OrderInterceptor oi = new OrderInterceptor();
            oi.setMaxQueue(ordersize);
            channel.addInterceptor(oi);
        }

        if ( async ) {
            MessageDispatchInterceptor mi = new MessageDispatch15Interceptor();
            mi.setMaxQueueSize(asyncsize);
            channel.addInterceptor(mi);
            System.out.println("Added MessageDispatchInterceptor");
        }

        if ( failuredetect ) {
            TcpFailureDetector tcpfi = new TcpFailureDetector();
            channel.addInterceptor(tcpfi);
        }
        if ( staticMembers.size() > 0 ) {
            StaticMembershipInterceptor smi = new StaticMembershipInterceptor();
            for (int x=0; x<staticMembers.size(); x++ ) {
                smi.addStaticMember(staticMembers.get(x));
            }
            channel.addInterceptor(smi);
        }


        byte[] domain = new byte[] {1,2,3,4,5,6,7,8,9,0};
        ((McastService)channel.getMembershipService()).setDomain(domain);
        DomainFilterInterceptor filter = new DomainFilterInterceptor();
        filter.setDomain(domain);
        channel.addInterceptor(filter);
        return channel;
    }

}