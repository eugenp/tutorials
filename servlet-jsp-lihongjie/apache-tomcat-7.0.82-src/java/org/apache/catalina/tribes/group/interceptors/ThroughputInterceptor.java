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
package org.apache.catalina.tribes.group.interceptors;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;
import org.apache.catalina.tribes.group.InterceptorPayload;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;



/**
 *
 *
 * @author Filip Hanik
 * @version 1.0
 */
public class ThroughputInterceptor extends ChannelInterceptorBase {
    private static final Log log = LogFactory.getLog(ThroughputInterceptor.class);

    double mbTx = 0;
    double mbAppTx = 0;
    double mbRx = 0;
    double timeTx = 0;
    double lastCnt = 0;
    AtomicLong msgTxCnt = new AtomicLong(1);
    AtomicLong msgRxCnt = new AtomicLong(0);
    AtomicLong msgTxErr = new AtomicLong(0);
    int interval = 10000;
    AtomicInteger access = new AtomicInteger(0);
    long txStart = 0;
    long rxStart = 0;
    DecimalFormat df = new DecimalFormat("#0.00");


    @Override
    public void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws ChannelException {
        if ( access.addAndGet(1) == 1 ) txStart = System.currentTimeMillis();
        long bytes = XByteBuffer.getDataPackageLength(((ChannelData)msg).getDataPackageLength());
        try {
            super.sendMessage(destination, msg, payload);
        }catch ( ChannelException x ) {
            msgTxErr.addAndGet(1);
            if ( access.get() == 1 ) access.addAndGet(-1);
            throw x;
        } 
        mbTx += (bytes*destination.length)/(1024d*1024d);
        mbAppTx += bytes/(1024d*1024d);
        if ( access.addAndGet(-1) == 0 ) {
            long stop = System.currentTimeMillis();
            timeTx += (stop - txStart) / 1000d;
            if ((msgTxCnt.get() / interval) >= lastCnt) {
                lastCnt++;
                report(timeTx);
            }
        }
        msgTxCnt.addAndGet(1);
    }

    @Override
    public void messageReceived(ChannelMessage msg) {
        if ( rxStart == 0 ) rxStart = System.currentTimeMillis();
        long bytes = XByteBuffer.getDataPackageLength(((ChannelData)msg).getDataPackageLength());
        mbRx += bytes/(1024d*1024d);
        msgRxCnt.addAndGet(1);
        if ( msgRxCnt.get() % interval == 0 ) report(timeTx);
        super.messageReceived(msg);
        
    }
    
    public void report(double timeTx) {
        StringBuilder buf = new StringBuilder("ThroughputInterceptor Report[\n\tTx Msg:");
        buf.append(msgTxCnt).append(" messages\n\tSent:");
        buf.append(df.format(mbTx));
        buf.append(" MB (total)\n\tSent:");
        buf.append(df.format(mbAppTx));
        buf.append(" MB (application)\n\tTime:");
        buf.append(df.format(timeTx));
        buf.append(" seconds\n\tTx Speed:");
        buf.append(df.format(mbTx/timeTx));
        buf.append(" MB/sec (total)\n\tTxSpeed:");
        buf.append(df.format(mbAppTx/timeTx));
        buf.append(" MB/sec (application)\n\tError Msg:");
        buf.append(msgTxErr).append("\n\tRx Msg:");
        buf.append(msgRxCnt);
        buf.append(" messages\n\tRx Speed:");
        buf.append(df.format(mbRx/((System.currentTimeMillis()-rxStart)/1000)));
        buf.append(" MB/sec (since 1st msg)\n\tReceived:");
        buf.append(df.format(mbRx)).append(" MB]\n");
        if ( log.isInfoEnabled() ) log.info(buf);
    }
    
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public double getLastCnt() {
        return lastCnt;
    }

    public double getMbAppTx() {
        return mbAppTx;
    }

    public double getMbRx() {
        return mbRx;
    }

    public double getMbTx() {
        return mbTx;
    }

    public AtomicLong getMsgRxCnt() {
        return msgRxCnt;
    }

    public AtomicLong getMsgTxCnt() {
        return msgTxCnt;
    }

    public AtomicLong getMsgTxErr() {
        return msgTxErr;
    }

    public long getRxStart() {
        return rxStart;
    }

    public double getTimeTx() {
        return timeTx;
    }

    public long getTxStart() {
        return txStart;
    }

}
