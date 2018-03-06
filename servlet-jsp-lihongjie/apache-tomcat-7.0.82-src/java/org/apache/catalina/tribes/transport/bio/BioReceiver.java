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
package org.apache.catalina.tribes.transport.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.io.ObjectReader;
import org.apache.catalina.tribes.transport.AbstractRxTask;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.RxTaskPool;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 *
 * @author Filip Hanik
 */
public class BioReceiver extends ReceiverBase implements Runnable {

    private static final Log log = LogFactory.getLog(BioReceiver.class);

    protected ServerSocket serverSocket;

    public BioReceiver() {
        // NO-OP
    }

    @Override
    public void start() throws IOException {
        super.start();
        try {
            setPool(new RxTaskPool(getMaxThreads(),getMinThreads(),this));
        } catch (Exception x) {
            log.fatal("ThreadPool can initilzed. Listener not started", x);
            if ( x instanceof IOException ) throw (IOException)x;
            else throw new IOException(x.getMessage());
        }
        try {
            getBind();
            bind();
            String channelName = "";
            if (getChannel() instanceof GroupChannel
                    && ((GroupChannel)getChannel()).getName() != null) {
                channelName = "[" + ((GroupChannel)getChannel()).getName() + "]";
            }
            Thread t = new Thread(this, "BioReceiver" + channelName);
            t.setDaemon(true);
            t.start();
        } catch (Exception x) {
            log.fatal("Unable to start cluster receiver", x);
            if ( x instanceof IOException ) throw (IOException)x;
            else throw new IOException(x.getMessage());
        }
    }
    
    @Override
    public AbstractRxTask createRxTask() {
        return getReplicationThread();
    }
    
    protected BioReplicationTask getReplicationThread() {
        BioReplicationTask result = new BioReplicationTask(this);
        result.setOptions(getWorkerThreadOptions());
        result.setUseBufferPool(this.getUseBufferPool());
        return result;
    }

    @Override
    public void stop() {
        setListen(false);
        try {
            this.serverSocket.close();
        } catch (Exception x) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to close socket", x);
            }
        }
        super.stop();
    }


    protected void bind() throws IOException {
        // allocate an unbound server socket channel
        serverSocket = new ServerSocket();
        // set the port the server channel will listen to
        //serverSocket.bind(new InetSocketAddress(getBind(), getTcpListenPort()));
        bind(serverSocket,getPort(),getAutoBind());
    }


    @Override
    public void run() {
        try {
            listen();
        } catch (Exception x) {
            log.error("Unable to run replication listener.", x);
        }
    }
    
    public void listen() throws Exception {
        if (doListen()) {
            log.warn("ServerSocket already started");
            return;
        }
        setListen(true);

        while ( doListen() ) {
            Socket socket = null;
            if ( getTaskPool().available() < 1 ) {
                if ( log.isWarnEnabled() )
                    log.warn("All BIO server replication threads are busy, unable to handle more requests until a thread is freed up.");
            }
            BioReplicationTask task = (BioReplicationTask)getTaskPool().getRxTask();
            if ( task == null ) continue; //should never happen
            try {
                socket = serverSocket.accept();
            }catch ( Exception x ) {
                if ( doListen() ) throw x;
            }
            if ( !doListen() ) {
                task.setDoRun(false);
                task.serviceSocket(null,null);
                getExecutor().execute(task);
                break; //regular shutdown
            }
            if ( socket == null ) continue;
            socket.setReceiveBufferSize(getRxBufSize());
            socket.setSendBufferSize(getTxBufSize());
            socket.setTcpNoDelay(getTcpNoDelay());
            socket.setKeepAlive(getSoKeepAlive());
            socket.setOOBInline(getOoBInline());
            socket.setReuseAddress(getSoReuseAddress());
            socket.setSoLinger(getSoLingerOn(),getSoLingerTime());
            socket.setSoTimeout(getTimeout());
            ObjectReader reader = new ObjectReader(socket);
            task.serviceSocket(socket,reader);
            getExecutor().execute(task);
        }//while
    }
    

}