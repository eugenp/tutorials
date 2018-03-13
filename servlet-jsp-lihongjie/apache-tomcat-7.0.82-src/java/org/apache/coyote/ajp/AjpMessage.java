/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.coyote.ajp;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.HexUtils;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.res.StringManager;

/**
 * A single packet for communication between the web server and the
 * container.  Designed to be reused many times with no creation of
 * garbage.  Understands the format of data types for these packets.
 * Can be used (somewhat confusingly) for both incoming and outgoing
 * packets.  
 *
 * @author Henri Gomez
 * @author Dan Milstein
 * @author Keith Wannamaker
 * @author Kevin Seguin
 * @author Costin Manolache
 */
public class AjpMessage {


    private static final Log log = LogFactory.getLog(AjpMessage.class);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    // ------------------------------------------------------------ Constructor

    
    public AjpMessage(int packetSize) {
        buf = new byte[packetSize];
    }
    

    // ----------------------------------------------------- Instance Variables


    /**
     * Fixed size buffer.
     */
    protected byte buf[] = null;


    /**
     * The current read or write position in the buffer.
     */
    protected int pos;


    /**
     * This actually means different things depending on whether the
     * packet is read or write.  For read, it's the length of the
     * payload (excluding the header).  For write, it's the length of
     * the packet as a whole (counting the header).  Oh, well.
     */
    protected int len; 

    
    // --------------------------------------------------------- Public Methods


    /**
     * Prepare this packet for accumulating a message from the container to
     * the web server.  Set the write position to just after the header
     * (but leave the length unwritten, because it is as yet unknown).
     */
    public void reset() {
        len = 4;
        pos = 4;
    }


    /**
     * For a packet to be sent to the web server, finish the process of
     * accumulating data and write the length of the data payload into
     * the header.  
     */
    public void end() {
        len = pos;
        int dLen = len - 4;

        buf[0] = (byte) 0x41;
        buf[1] = (byte) 0x42;
        buf[2] = (byte) ((dLen>>>8) & 0xFF);
        buf[3] = (byte) (dLen & 0xFF);
    }


    /**
     * Return the underlying byte buffer.
     */
    public byte[] getBuffer() {
        return buf;
    }


    /**
     * Return the current message length. For read, it's the length of the
     * payload (excluding the header).  For write, it's the length of
     * the packet as a whole (counting the header).
     */
    public int getLen() {
        return len;
    }
    

    /**
     * Add a short integer (2 bytes) to the message.
     */
    public void appendInt(int val) {
        buf[pos++] = (byte) ((val >>> 8) & 0xFF);
        buf[pos++] = (byte) (val & 0xFF);
    }


    /**
     * Append a byte (1 byte) to the message.
     */
    public void appendByte(int val) {
        buf[pos++] = (byte) val;
    }

    
    /**
     * Write a MessageBytes out at the current write position.
     * A null MessageBytes is encoded as a string with length 0.  
     */
    public void appendBytes(MessageBytes mb) {
        if (mb == null) {
            log.error(sm.getString("ajpmessage.null"), 
                    new NullPointerException());
            appendInt(0);
            appendByte(0);
            return;
        }
        if (mb.getType() == MessageBytes.T_BYTES) {
            ByteChunk bc = mb.getByteChunk();
            appendByteChunk(bc);
        } else if (mb.getType() == MessageBytes.T_CHARS) {
            CharChunk cc = mb.getCharChunk();
            appendCharChunk(cc);
        } else {
            appendString(mb.toString());
        }
    }

    
    /**
     * Write a ByteChunk out at the current write position.
     * A null ByteChunk is encoded as a string with length 0.  
     */
    public void appendByteChunk(ByteChunk bc) {
        if (bc == null) {
            log.error(sm.getString("ajpmessage.null"), 
                    new NullPointerException());
            appendInt(0);
            appendByte(0);
            return;
        }
        appendBytes(bc.getBytes(), bc.getStart(), bc.getLength());
    }

    
    /**
     * Write a CharChunk out at the current write position.
     * A null CharChunk is encoded as a string with length 0.  
     */
    public void appendCharChunk(CharChunk cc) {
        if (cc == null) {
            log.error(sm.getString("ajpmessage.null"), 
                    new NullPointerException());
            appendInt(0);
            appendByte(0);
            return;
        }
        int start = cc.getStart();
        int end = cc.getEnd();
        appendInt(end - start);
        char[] cbuf = cc.getBuffer();
        for (int i = start; i < end; i++) {
            char c = cbuf[i];
            // Note:  This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework.  It must suffice until servlet output
            // streams properly encode their output.
            if (((c <= 31) && (c != 9)) || c == 127 || c > 255) {
                c = ' ';
            }
            appendByte(c);
        }
        appendByte(0);
    }

    
    /**
     * Write a String out at the current write position.  Strings are
     * encoded with the length in two bytes first, then the string, and
     * then a terminating \0 (which is <B>not</B> included in the
     * encoded length).  The terminator is for the convenience of the C
     * code, where it saves a round of copying.  A null string is
     * encoded as a string with length 0.  
     */
    public void appendString(String str) {
        if (str == null) {
            log.error(sm.getString("ajpmessage.null"), 
                    new NullPointerException());
            appendInt(0);
            appendByte(0);
            return;
        }
        int len = str.length();
        appendInt(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt (i);
            // Note:  This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework.  It must suffice until servlet output
            // streams properly encode their output.
            if (((c <= 31) && (c != 9)) || c == 127 || c > 255) {
                c = ' ';
            }
            appendByte(c);
        }
        appendByte(0);
    }

    
    /** 
     * Copy a chunk of bytes into the packet, starting at the current
     * write position.  The chunk of bytes is encoded with the length
     * in two bytes first, then the data itself, and finally a
     * terminating \0 (which is <B>not</B> included in the encoded
     * length).
     *
     * @param b The array from which to copy bytes.
     * @param off The offset into the array at which to start copying
     * @param numBytes The number of bytes to copy.  
     */
    public void appendBytes(byte[] b, int off, int numBytes) {
        if (pos + numBytes + 3 > buf.length) {
            log.error(sm.getString("ajpmessage.overflow", "" + numBytes, "" + pos),
                    new ArrayIndexOutOfBoundsException());
            if (log.isDebugEnabled()) {
                dump("Overflow/coBytes");
            }
            return;
        }
        appendInt(numBytes);
        System.arraycopy(b, off, buf, pos, numBytes);
        pos += numBytes;
        appendByte(0);
    }

    
    /**
     * Read an integer from packet, and advance the read position past
     * it.  Integers are encoded as two unsigned bytes with the
     * high-order byte first, and, as far as I can tell, in
     * little-endian order within each byte.  
     */
    public int getInt() {
        int b1 = buf[pos++] & 0xFF;
        int b2 = buf[pos++] & 0xFF;
        validatePos(pos);
        return (b1<<8) + b2;
    }


    public int peekInt() {
        validatePos(pos + 2);
        int b1 = buf[pos] & 0xFF;
        int b2 = buf[pos+1] & 0xFF;
        return (b1<<8) + b2;
    }

    
    public byte getByte() {
        byte res = buf[pos++];
        validatePos(pos);
        return res;
    }

    
    public void getBytes(MessageBytes mb) {
        doGetBytes(mb, true);
    }
    
    public void getBodyBytes(MessageBytes mb) {
        doGetBytes(mb, false);
    }
    
    private void doGetBytes(MessageBytes mb, boolean terminated) {
        int length = getInt();
        if ((length == 0xFFFF) || (length == -1)) {
            mb.recycle();
            return;
        }
        if (terminated) {
            validatePos(pos + length + 1);
        } else {
            validatePos(pos + length);
        }
        mb.setBytes(buf, pos, length);
        mb.getCharChunk().recycle(); // not valid anymore
        pos += length;
        if (terminated) {
            pos++; // Skip the terminating \0
        }
    }
    
    
    /**
     * Read a 32 bits integer from packet, and advance the read position past
     * it.  Integers are encoded as four unsigned bytes with the
     * high-order byte first, and, as far as I can tell, in
     * little-endian order within each byte.
     */
    public int getLongInt() {
        int b1 = buf[pos++] & 0xFF; // No swap, Java order
        b1 <<= 8;
        b1 |= (buf[pos++] & 0xFF);
        b1 <<= 8;
        b1 |= (buf[pos++] & 0xFF);
        b1 <<=8;
        b1 |= (buf[pos++] & 0xFF);
        validatePos(pos);
        return  b1;
    }


    public int getHeaderLength() {
        return Constants.H_SIZE;
    }

    
    public int getPacketSize() {
        return buf.length;
    }
    
    @Deprecated
    public int processHeader() {
        return processHeader(true);
    }
    
    public int processHeader(boolean toContainer) {
        pos = 0;
        int mark = getInt();
        len = getInt();
        // Verify message signature
        if ((toContainer && mark != 0x1234) ||
                (!toContainer && mark != 0x4142)) {
            log.error(sm.getString("ajpmessage.invalid", "" + mark));
            if (log.isDebugEnabled()) {
                dump("In: ");
            }
            return -1;
        }
        if (log.isDebugEnabled())  {
            log.debug("Received " + len + " " + buf[0]);
        }
        return len;
    }
    

    /**
     * Dump the contents of the message, prefixed with the given String.
     */
    public void dump(String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg + ": " + HexUtils.toHexString(buf) + " " + pos +"/" + (len + 4));
        }
        int max = pos;
        if (len + 4 > pos)
            max = len+4;
        if (max > 1000)
            max = 1000;
        if (log.isDebugEnabled()) {
            for (int j = 0; j < max; j += 16) { 
                log.debug(hexLine(buf, j, len));
            }
        }
    }


    private void validatePos(int posToTest) {
        if (posToTest > len + 4) {
            // Trying to read data beyond the end of the AJP message 
            throw new ArrayIndexOutOfBoundsException(sm.getString(
                    "ajpMessage.invalidPos", Integer.valueOf(posToTest)));
        }
    }
    // ------------------------------------------------------ Protected Methods


    protected static String hexLine(byte buf[], int start, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + 16 ; i++) {
            if (i < len + 4) {
                sb.append(hex(buf[i]) + " ");
            } else { 
                sb.append("   ");
            }
        }
        sb.append(" | ");
        for (int i = start; i < start + 16 && i < len + 4; i++) {
            if (!Character.isISOControl((char) buf[i])) {
                sb.append(Character.valueOf((char) buf[i]));
            } else {
                sb.append(".");
            }
        }
        return sb.toString();
    }


    protected static String hex(int x) {
        String h = Integer.toHexString(x);
        if (h.length() == 1) {
            h = "0" + h;
        }
        return h.substring(h.length() - 2);
    }


}
