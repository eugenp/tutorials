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

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;
import org.apache.catalina.tribes.group.InterceptorPayload;
import org.apache.catalina.tribes.io.XByteBuffer;


/**
 *
 * The order interceptor guarantees that messages are received in the same order they were 
 * sent.
 * This interceptor works best with the ack=true setting. <br>
 * There is no point in 
 * using this with the replicationMode="fastasynchqueue" as this mode guarantees ordering.<BR>
 * If you are using the mode ack=false replicationMode=pooled, and have a lot of concurrent threads,
 * this interceptor can really slow you down, as many messages will be completely out of order
 * and the queue might become rather large. If this is the case, then you might want to set 
 * the value OrderInterceptor.maxQueue = 25 (meaning that we will never keep more than 25 messages in our queue)
 * <br><b>Configuration Options</b><br>
 * OrderInterceptor.expire=<milliseconds> - if a message arrives out of order, how long before we act on it <b>default=3000ms</b><br>
 * OrderInterceptor.maxQueue=<max queue size> - how much can the queue grow to ensure ordering. 
 *   This setting is useful to avoid OutOfMemoryErrors<b>default=Integer.MAX_VALUE</b><br>
 * OrderInterceptor.forwardExpired=<boolean> - this flag tells the interceptor what to 
 * do when a message has expired or the queue has grown larger than the maxQueue value.
 * true means that the message is sent up the stack to the receiver that will receive and out of order message
 * false means, forget the message and reset the message counter. <b>default=true</b>
 * 
 * 
 * @author Filip Hanik
 * @version 1.1
 */
public class OrderInterceptor extends ChannelInterceptorBase {
    private HashMap<Member, Counter> outcounter = new HashMap<Member, Counter>();
    private HashMap<Member, Counter> incounter = new HashMap<Member, Counter>();
    private HashMap<Member, MessageOrder> incoming = new HashMap<Member, MessageOrder>();
    private long expire = 3000;
    private boolean forwardExpired = true;
    private int maxQueue = Integer.MAX_VALUE;
    
    final ReentrantReadWriteLock inLock = new ReentrantReadWriteLock(true);
    final ReentrantReadWriteLock outLock= new ReentrantReadWriteLock(true);

    @Override
    public void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws ChannelException {
        if ( !okToProcess(msg.getOptions()) ) {
            super.sendMessage(destination, msg, payload);
            return;
        }
        ChannelException cx = null;
        for (int i=0; i<destination.length; i++ ) {
            try {
                int nr = 0;
                try {
                    outLock.writeLock().lock();
                    nr = incCounter(destination[i]);
                } finally {
                    outLock.writeLock().unlock();
                }
                //reduce byte copy
                msg.getMessage().append(nr);
                try {
                    getNext().sendMessage(new Member[] {destination[i]}, msg, payload);
                } finally {
                    msg.getMessage().trim(4);
                }
            }catch ( ChannelException x ) {
                if ( cx == null ) cx = x;
                cx.addFaultyMember(x.getFaultyMembers());
            }
        }//for
        if ( cx != null ) throw cx;
    }

    @Override
    public void messageReceived(ChannelMessage msg) {
        if ( !okToProcess(msg.getOptions()) ) {
            super.messageReceived(msg);
            return;
        }
        int msgnr = XByteBuffer.toInt(msg.getMessage().getBytesDirect(),msg.getMessage().getLength()-4);
        msg.getMessage().trim(4);
        MessageOrder order = new MessageOrder(msgnr,(ChannelMessage)msg.deepclone());
        try {
            inLock.writeLock().lock();
            if ( processIncoming(order) ) processLeftOvers(msg.getAddress(),false);
        }finally {
            inLock.writeLock().unlock();
        }
    }
    protected void processLeftOvers(Member member, boolean force) {
        MessageOrder tmp = incoming.get(member);
        if ( force ) {
            Counter cnt = getInCounter(member);
            cnt.setCounter(Integer.MAX_VALUE);
        }
        if ( tmp!= null ) processIncoming(tmp);
    }
    /**
     * 
     * @param order MessageOrder
     * @return boolean - true if a message expired and was processed
     */
    protected boolean processIncoming(MessageOrder order) {
        boolean result = false;
        Member member = order.getMessage().getAddress();
        Counter cnt = getInCounter(member);
        
        MessageOrder tmp = incoming.get(member);
        if ( tmp != null ) {
            order = MessageOrder.add(tmp,order);
        }
        
        
        while ( (order!=null) && (order.getMsgNr() <= cnt.getCounter())  ) {
            //we are right on target. process orders
            if ( order.getMsgNr() == cnt.getCounter() ) cnt.inc();
            else if ( order.getMsgNr() > cnt.getCounter() ) cnt.setCounter(order.getMsgNr());
            super.messageReceived(order.getMessage());
            order.setMessage(null);
            order = order.next;
        }
        MessageOrder head = order;
        MessageOrder prev = null;
        tmp = order;
        //flag to empty out the queue when it larger than maxQueue
        boolean empty = order!=null?order.getCount()>=maxQueue:false;
        while ( tmp != null ) {
            //process expired messages or empty out the queue
            if ( tmp.isExpired(expire) || empty ) {
                //reset the head
                if ( tmp == head ) head = tmp.next;
                cnt.setCounter(tmp.getMsgNr()+1);
                if ( getForwardExpired() ) 
                    super.messageReceived(tmp.getMessage());
                tmp.setMessage(null);
                tmp = tmp.next;
                if ( prev != null ) prev.next = tmp;  
                result = true;
            } else {
                prev = tmp;
                tmp = tmp.next;
            }
        }
        if ( head == null ) incoming.remove(member);
        else incoming.put(member, head);
        return result;
    }
    
    @Override
    public void memberAdded(Member member) {
        //notify upwards
        super.memberAdded(member);
    }

    @Override
    public void memberDisappeared(Member member) {
        //reset counters - lock free
        incounter.remove(member);
        outcounter.remove(member);
        //clear the remaining queue
        processLeftOvers(member,true);
        //notify upwards
        super.memberDisappeared(member);
    }
    
    protected int incCounter(Member mbr) { 
        Counter cnt = getOutCounter(mbr);
        return cnt.inc();
    }
    
    protected Counter getInCounter(Member mbr) {
        Counter cnt = incounter.get(mbr);
        if ( cnt == null ) {
            cnt = new Counter();
            cnt.inc(); //always start at 1 for incoming
            incounter.put(mbr,cnt);
        }
        return cnt;
    }

    protected Counter getOutCounter(Member mbr) {
        Counter cnt = outcounter.get(mbr);
        if ( cnt == null ) {
            cnt = new Counter();
            outcounter.put(mbr,cnt);
        }
        return cnt;
    }

    protected static class Counter {
        private AtomicInteger value = new AtomicInteger(0);
        
        public int getCounter() {
            return value.get();
        }
        
        public void setCounter(int counter) {
            this.value.set(counter);
        }
        
        public int inc() {
            return value.addAndGet(1);
        }
    }
    
    protected static class MessageOrder {
        private long received = System.currentTimeMillis();
        private MessageOrder next;
        private int msgNr;
        private ChannelMessage msg = null;
        public MessageOrder(int msgNr,ChannelMessage msg) {
            this.msgNr = msgNr;
            this.msg = msg;
        }
        
        public boolean isExpired(long expireTime) {
            return (System.currentTimeMillis()-received) > expireTime;
        }
        
        public ChannelMessage getMessage() {
            return msg;
        }
        
        public void setMessage(ChannelMessage msg) {
            this.msg = msg;
        }
        
        public void setNext(MessageOrder order) {
            this.next = order;
        }
        public MessageOrder getNext() {
            return next;
        }
        
        public int getCount() {
            int counter = 1;
            MessageOrder tmp = next;
            while ( tmp != null ) {
                counter++;
                tmp = tmp.next;
            }
            return counter;
        }
        
        @SuppressWarnings("null") // prev cannot be null
        public static MessageOrder add(MessageOrder head, MessageOrder add) {
            if ( head == null ) return add;
            if ( add == null ) return head;
            if ( head == add ) return add;

            if ( head.getMsgNr() > add.getMsgNr() ) {
                add.next = head;
                return add;
            }
            
            MessageOrder iter = head;
            MessageOrder prev = null;
            while ( iter.getMsgNr() < add.getMsgNr() && (iter.next !=null ) ) {
                prev = iter;
                iter = iter.next;
            }
            if ( iter.getMsgNr() < add.getMsgNr() ) {
                //add after
                add.next = iter.next;
                iter.next = add;
            } else if (iter.getMsgNr() > add.getMsgNr()) {
                //add before
                prev.next = add; // prev cannot be null here, warning suppressed
                add.next = iter;
                
            } else {
                throw new ArithmeticException("Message added has the same counter, synchronization bug. Disable the order interceptor");
            }
            
            return head;
        }
        
        public int getMsgNr() {
            return msgNr;
        }


    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setForwardExpired(boolean forwardExpired) {
        this.forwardExpired = forwardExpired;
    }

    public void setMaxQueue(int maxQueue) {
        this.maxQueue = maxQueue;
    }

    public long getExpire() {
        return expire;
    }

    public boolean getForwardExpired() {
        return forwardExpired;
    }

    public int getMaxQueue() {
        return maxQueue;
    }

}
