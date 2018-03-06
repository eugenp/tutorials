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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import org.apache.catalina.tribes.RemoteProcessException;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.transport.AbstractSender;
import org.apache.catalina.tribes.transport.Constants;
import org.apache.catalina.tribes.transport.SenderState;
import org.apache.catalina.tribes.util.StringManager;

/**
 * Send cluster messages with only one socket. Ack and keep Alive Handling is
 * supported
 * 
 * @author Peter Rossbach
 * @author Filip Hanik
 * @since 5.5.16
 */
public class BioSender extends AbstractSender {

    private static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog(BioSender.class);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    // ----------------------------------------------------- Instance Variables

    /**
     * The descriptive information about this implementation.
     */
    private static final String info = "DataSender/3.0";

    
    /**
     * current sender socket
     */
    private Socket socket = null;
    private OutputStream soOut = null;
    private InputStream soIn = null;
    
    protected XByteBuffer ackbuf = new XByteBuffer(Constants.ACK_COMMAND.length,true);


    // ------------------------------------------------------------- Constructor
    
    public BioSender()  {
        // NO-OP
    }


    // ------------------------------------------------------------- Properties

    /**
     * Return descriptive information about this implementation and the
     * corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return (info);
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Connect other cluster member receiver 
     * @see org.apache.catalina.tribes.transport.DataSender#connect()
     */
    @Override
    public  void connect() throws IOException {
        openSocket();
   }

 
    /**
     * disconnect and close socket
     * 
     * @see org.apache.catalina.tribes.transport.DataSender#disconnect()
     */
    @Override
    public  void disconnect() {
        boolean connect = isConnected();
        closeSocket();
        if (connect) {
            if (log.isDebugEnabled())
                log.debug(sm.getString("IDataSender.disconnect", getAddress().getHostAddress(), Integer.valueOf(getPort()), Long.valueOf(0)));
        }
        
    }

    /**
     * Send message.
     */
    public  void sendMessage(byte[] data, boolean waitForAck) throws IOException {
        IOException exception = null;
        setAttempt(0);
        try {
             // first try with existing connection
             pushMessage(data,false,waitForAck);
        } catch (IOException x) {
            SenderState.getSenderState(getDestination()).setSuspect();
            exception = x;
            if (log.isTraceEnabled()) log.trace(sm.getString("IDataSender.send.again", getAddress().getHostAddress(), Integer.valueOf(getPort())),x);
            while ( getAttempt()<getMaxRetryAttempts() ) {
                try {
                    setAttempt(getAttempt()+1);
                    // second try with fresh connection
                    pushMessage(data, true,waitForAck);
                    exception = null;
                } catch (IOException xx) {
                    exception = xx;
                    closeSocket();
                }
            }
        } finally {
            setRequestCount(getRequestCount()+1);
            keepalive();
            if ( exception != null ) throw exception;
        }
    }

    
    /**
     * Name of this SockerSender
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("DataSender[(");
        buf.append(super.toString()).append(")");
        buf.append(getAddress()).append(":").append(getPort()).append("]");
        return buf.toString();
    }

    // --------------------------------------------------------- Protected Methods
 
    /**
     * open real socket and set time out when waitForAck is enabled
     * is socket open return directly
     */
    protected void openSocket() throws IOException {
       if(isConnected()) return ;
       try {
           socket = new Socket();
           InetSocketAddress sockaddr = new InetSocketAddress(getAddress(), getPort());
           socket.connect(sockaddr,(int)getTimeout());
           socket.setSendBufferSize(getTxBufSize());
           socket.setReceiveBufferSize(getRxBufSize());
           socket.setSoTimeout( (int) getTimeout());
           socket.setTcpNoDelay(getTcpNoDelay());
           socket.setKeepAlive(getSoKeepAlive());
           socket.setReuseAddress(getSoReuseAddress());
           socket.setOOBInline(getOoBInline());
           socket.setSoLinger(getSoLingerOn(),getSoLingerTime());
           socket.setTrafficClass(getSoTrafficClass());
           setConnected(true);
           soOut = socket.getOutputStream();
           soIn  = socket.getInputStream();
           setRequestCount(0);
           setConnectTime(System.currentTimeMillis());
           if (log.isDebugEnabled())
               log.debug(sm.getString("IDataSender.openSocket", getAddress().getHostAddress(), Integer.valueOf(getPort()), Long.valueOf(0)));
      } catch (IOException ex1) {
          SenderState.getSenderState(getDestination()).setSuspect();
          if (log.isDebugEnabled())
              log.debug(sm.getString("IDataSender.openSocket.failure",getAddress().getHostAddress(), Integer.valueOf(getPort()), Long.valueOf(0)), ex1);
          throw (ex1);
        }
        
     }

    /**
     * Close socket.
     * 
     * @see #disconnect()
     */
    protected void closeSocket() {
        if(isConnected()) {
             if (socket != null) {
                try {
                    socket.close();
                } catch (IOException x) {
                    // Ignore
                } finally {
                    socket = null;
                    soOut = null;
                    soIn = null;
                }
            }
            setRequestCount(0);
            setConnected(false);
            if (log.isDebugEnabled())
                log.debug(sm.getString("IDataSender.closeSocket",getAddress().getHostAddress(), Integer.valueOf(getPort()), Long.valueOf(0)));
       }
    }

    /**
     * Push messages with only one socket at a time
     * Wait for ack is needed and make auto retry when write message is failed.
     * After sending error close and reopen socket again.
     * 
     * After successful sending update stats
     * 
     * WARNING: Subclasses must be very careful that only one thread call this pushMessage at once!!!
     * 
     * @see #closeSocket()
     * @see #openSocket()
     * @see #sendMessage(byte[], boolean)
     * 
     * @param data
     *            data to send
     * @since 5.5.10
     */
    
    protected void pushMessage(byte[] data, boolean reconnect, boolean waitForAck) throws IOException {
        keepalive();
        if ( reconnect ) closeSocket();
        if (!isConnected()) openSocket();
        soOut.write(data);
        soOut.flush();
        if (waitForAck) waitForAck();
        SenderState.getSenderState(getDestination()).setReady();

    }
    
    /**
     * Wait for Acknowledgement from other server.
     * FIXME Please, not wait only for three characters, better control that the wait ack message is correct.
     * @throws java.io.IOException
     * @throws java.net.SocketTimeoutException
     */
    protected void waitForAck() throws java.io.IOException {
        try {
            boolean ackReceived = false;
            boolean failAckReceived = false;
            ackbuf.clear();
            int bytesRead = 0;
            int i = soIn.read();
            while ((i != -1) && (bytesRead < Constants.ACK_COMMAND.length)) {
                bytesRead++;
                byte d = (byte)i;
                ackbuf.append(d);
                if (ackbuf.doesPackageExist() ) {
                    byte[] ackcmd = ackbuf.extractDataPackage(true).getBytes();
                    ackReceived = Arrays.equals(ackcmd,org.apache.catalina.tribes.transport.Constants.ACK_DATA);
                    failAckReceived = Arrays.equals(ackcmd,org.apache.catalina.tribes.transport.Constants.FAIL_ACK_DATA);
                    ackReceived = ackReceived || failAckReceived;
                    break;
                }
                i = soIn.read();
            }
            if (!ackReceived) {
                if (i == -1) throw new IOException(sm.getString("IDataSender.ack.eof",getAddress(), Integer.valueOf(socket.getLocalPort())));
                else throw new IOException(sm.getString("IDataSender.ack.wrong",getAddress(), Integer.valueOf(socket.getLocalPort())));
            } else if ( failAckReceived && getThrowOnFailedAck()) {
                throw new RemoteProcessException("Received a failed ack:org.apache.catalina.tribes.transport.Constants.FAIL_ACK_DATA");
            }
        } catch (IOException x) {
            String errmsg = sm.getString("IDataSender.ack.missing", getAddress(), Integer.valueOf(socket.getLocalPort()), Long.valueOf(getTimeout()));
            if ( SenderState.getSenderState(getDestination()).isReady() ) {
                SenderState.getSenderState(getDestination()).setSuspect();
                if ( log.isWarnEnabled() ) log.warn(errmsg, x);
            } else {
                if ( log.isDebugEnabled() )log.debug(errmsg, x);
            }
            throw x;
        } finally {
            ackbuf.clear();
        }
    }
}
