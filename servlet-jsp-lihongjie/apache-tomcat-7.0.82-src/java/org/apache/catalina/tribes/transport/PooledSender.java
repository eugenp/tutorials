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
package org.apache.catalina.tribes.transport;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.util.StringManager;
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
public abstract class PooledSender extends AbstractSender implements MultiPointSender {
    
    private static final Log log = LogFactory.getLog(PooledSender.class);
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);
    
    private SenderQueue queue = null;
    private int poolSize = 25;
    private long maxWait = 3000;
    public PooledSender() {
        queue = new SenderQueue(this,poolSize);
    }
    
    public abstract DataSender getNewDataSender();
    
    public DataSender getSender() {
        return queue.getSender(getMaxWait());
    }
    
    public void returnSender(DataSender sender) {
        sender.keepalive();
        queue.returnSender(sender);
    }
    
    @Override
    public synchronized void connect() throws IOException {
        //do nothing, happens in the socket sender itself
        queue.open();
        setConnected(true);
    }
    
    @Override
    public synchronized void disconnect() {
        queue.close();
        setConnected(false);
    }
    
    
    public int getInPoolSize() {
        return queue.getInPoolSize();
    }

    public int getInUsePoolSize() {
        return queue.getInUsePoolSize();
    }


    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        queue.setLimit(poolSize);
    }

    public int getPoolSize() {
        return poolSize;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    @Override
    public boolean keepalive() {
        //do nothing, the pool checks on every return
        return (queue==null)?false:queue.checkIdleKeepAlive();
    }

    @Override
    public void add(Member member) {
        // no op, senders created upon demands
    }

    @Override
    public void remove(Member member) {
        //no op for now, should not cancel out any keys
        //can create serious sync issues
        //all TCP connections are cleared out through keepalive
        //and if remote node disappears
    }
    //  ----------------------------------------------------- Inner Class

    private static class SenderQueue {
        private int limit = 25;

        PooledSender parent = null;

        private List<DataSender> notinuse = null;

        private List<DataSender> inuse = null;

        private boolean isOpen = true;

        public SenderQueue(PooledSender parent, int limit) {
            this.limit = limit;
            this.parent = parent;
            notinuse = new java.util.LinkedList<DataSender>();
            inuse = new java.util.LinkedList<DataSender>();
        }

        /**
         * @return Returns the limit.
         */
        public int getLimit() {
            return limit;
        }
        /**
         * @param limit The limit to set.
         */
        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getInUsePoolSize() {
            return inuse.size();
        }

        public int getInPoolSize() {
            return notinuse.size();
        }
        
        public synchronized boolean checkIdleKeepAlive() {
            DataSender[] list = new DataSender[notinuse.size()];
            notinuse.toArray(list);
            boolean result = false;
            for (int i=0; i<list.length; i++) {
                result = result | list[i].keepalive();
            }
            return result;
        }

        public synchronized DataSender getSender(long timeout) {
            long start = System.currentTimeMillis();
            while ( true ) {
                if (!isOpen)throw new IllegalStateException("Queue is closed");
                DataSender sender = null;
                if (notinuse.size() == 0 && inuse.size() < limit) {
                    sender = parent.getNewDataSender();
                } else if (notinuse.size() > 0) {
                    sender = notinuse.remove(0);
                }
                if (sender != null) {
                    inuse.add(sender);
                    return sender;
                }//end if
                long delta = System.currentTimeMillis() - start;
                if ( delta > timeout && timeout>0) return null;
                else {
                    try {
                        wait(Math.max(timeout - delta,1));
                    }catch (InterruptedException x){}
                }//end if
            }
        }

        public synchronized void returnSender(DataSender sender) {
            if ( !isOpen) {
                sender.disconnect();
                return;
            }
            //to do
            inuse.remove(sender);
            //just in case the limit has changed
            if ( notinuse.size() < this.getLimit() ) notinuse.add(sender);
            else
                try {
                    sender.disconnect();
                } catch (Exception e) {
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString(
                                "PooledSender.senderDisconnectFail"), e);
                    }
                }
            notify();
        }

        public synchronized void close() {
            isOpen = false;
            Object[] unused = notinuse.toArray();
            Object[] used = inuse.toArray();
            for (int i = 0; i < unused.length; i++) {
                DataSender sender = (DataSender) unused[i];
                sender.disconnect();
            }//for
            for (int i = 0; i < used.length; i++) {
                DataSender sender = (DataSender) used[i];
                sender.disconnect();
            }//for
            notinuse.clear();
            inuse.clear();
            notify();


        }

        public synchronized void open() {
            isOpen = true;
            notify();
        }
    }
}