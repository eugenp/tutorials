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

package org.apache.catalina.tribes.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The XByteBuffer provides a dual functionality.
 * One, it stores message bytes and automatically extends the byte buffer if needed.<BR>
 * Two, it can encode and decode packages so that they can be defined and identified
 * as they come in on a socket.
 * <br>
 * <b>THIS CLASS IS NOT THREAD SAFE</B><BR>
 * <br>
 * Transfer package:
 * <ul>
 * <li><b>START_DATA/b> - 7 bytes - <i>FLT2002</i></li>
 * <li><b>SIZE</b>      - 4 bytes - size of the data package</li>
 * <li><b>DATA</b>      - should be as many bytes as the prev SIZE</li>
 * <li><b>END_DATA</b>  - 7 bytes - <i>TLF2003</i></lI>
 * </ul>
 * @author Filip Hanik
 */
public class XByteBuffer
{
    
    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog( XByteBuffer.class );
    
    /**
     * This is a package header, 7 bytes (FLT2002)
     */
    private static final byte[] START_DATA = {70,76,84,50,48,48,50};
    
    /**
     * This is the package footer, 7 bytes (TLF2003)
     */
    private static final byte[] END_DATA = {84,76,70,50,48,48,51};
 
    /**
     * Variable to hold the data
     */
    protected byte[] buf = null;
    
    /**
     * Current length of data in the buffer
     */
    protected int bufSize = 0;
    
    /**
     * Flag for discarding invalid packages
     * If this flag is set to true, and append(byte[],...) is called,
     * the data added will be inspected, and if it doesn't start with 
     * <code>START_DATA</code> it will be thrown away.
     * 
     */
    protected boolean discard = true;
    
    /**
     * Constructs a new XByteBuffer.<br>
     * TODO use a pool of byte[] for performance
     * @param size - the initial size of the byte buffer
     */
    public XByteBuffer(int size, boolean discard) {
        buf = new byte[size];
        this.discard = discard;
    }
    
    public XByteBuffer(byte[] data,boolean discard) {
        this(data,data.length+128,discard);
    }
    
    public XByteBuffer(byte[] data, int size,boolean discard) {
        int length = Math.max(data.length,size);
        buf = new byte[length];
        System.arraycopy(data,0,buf,0,data.length);
        bufSize = data.length;
        this.discard = discard;
    }
    
    public int getLength() {
        return bufSize;
    }

    public void setLength(int size) {
        if ( size > buf.length ) throw new ArrayIndexOutOfBoundsException("Size is larger than existing buffer.");
        bufSize = size;
    }
    
    public void trim(int length) {
        if ( (bufSize - length) < 0 ) 
            throw new ArrayIndexOutOfBoundsException("Can't trim more bytes than are available. length:"+bufSize+" trim:"+length);
        bufSize -= length;
    }
    
    public void reset() {
        bufSize = 0;
    }
            
    public byte[] getBytesDirect() {
        return this.buf;
    }

    /**
     * Returns the bytes in the buffer, in its exact length
     */
    public byte[] getBytes() {
        byte[] b = new byte[bufSize];
        System.arraycopy(buf,0,b,0,bufSize);
        return b;
    }

    /**
     * Resets the buffer
     */
    public void clear() {
        bufSize = 0;
    }

    /**
     * Appends the data to the buffer. If the data is incorrectly formatted, ie, the data should always start with the
     * header, false will be returned and the data will be discarded.
     * @param b - bytes to be appended
     * @param len - the number of bytes to append.
     * @return true if the data was appended correctly. Returns false if the package is incorrect, ie missing header or something, or the length of data is 0
     */
    public boolean append(ByteBuffer b, int len) {
        int newcount = bufSize + len;
        if (newcount > buf.length) {
            expand(newcount);
        }
        b.get(buf,bufSize,len);
        
        bufSize = newcount;
        
        if ( discard ) {
            if (bufSize > START_DATA.length && (firstIndexOf(buf, 0, START_DATA) == -1)) {
                bufSize = 0;
                log.error("Discarded the package, invalid header");
                return false;
            }
        }
        return true;

    }
    
    public boolean append(byte i) {
        int newcount = bufSize + 1;
        if (newcount > buf.length) {
            expand(newcount);
        }
        buf[bufSize] = i;
        bufSize = newcount;
        return true;
    }


    public boolean append(boolean i) {
        int newcount = bufSize + 1;
        if (newcount > buf.length) {
            expand(newcount);
        }
        XByteBuffer.toBytes(i,buf,bufSize);
        bufSize = newcount;
        return true;
    }

    public boolean append(long i) {
        int newcount = bufSize + 8;
        if (newcount > buf.length) {
            expand(newcount);
        }
        XByteBuffer.toBytes(i,buf,bufSize);
        bufSize = newcount;
        return true;
    }
    
    public boolean append(int i) {
        int newcount = bufSize + 4;
        if (newcount > buf.length) {
            expand(newcount);
        }
        XByteBuffer.toBytes(i,buf,bufSize);
        bufSize = newcount;
        return true;
    }

    public boolean append(byte[] b, int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) > b.length) || ((off + len) < 0))  {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return false;
        }

        int newcount = bufSize + len;
        if (newcount > buf.length) {
            expand(newcount);
        }
        System.arraycopy(b, off, buf, bufSize, len);
        bufSize = newcount;

        if ( discard ) {
            if (bufSize > START_DATA.length && (firstIndexOf(buf, 0, START_DATA) == -1)) {
                bufSize = 0;
                log.error("Discarded the package, invalid header");
                return false;
            }
        }
        return true;
    }

    public void expand(int newcount) {
        //don't change the allocation strategy
        byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
        System.arraycopy(buf, 0, newbuf, 0, bufSize);
        buf = newbuf;
    }
    
    public int getCapacity() {
        return buf.length;
    }


    /**
     * Internal mechanism to make a check if a complete package exists
     * within the buffer
     * @return - true if a complete package (header,compress,size,data,footer) exists within the buffer
     */
    public int countPackages() {
        return countPackages(false);
    }

    public int countPackages(boolean first)
    {
        int cnt = 0;
        int pos = START_DATA.length;
        int start = 0;

        while ( start < bufSize ) {
            //first check start header
            int index = XByteBuffer.firstIndexOf(buf,start,START_DATA);
            //if the header (START_DATA) isn't the first thing or
            //the buffer isn't even 14 bytes
            if ( index != start || ((bufSize-start)<14) ) break;
            //next 4 bytes are compress flag not needed for count packages
            //then get the size 4 bytes
            int size = toInt(buf, pos);
            //now the total buffer has to be long enough to hold
            //START_DATA.length+4+size+END_DATA.length
            pos = start + START_DATA.length + 4 + size;
            if ( (pos + END_DATA.length) > bufSize) break;
            //and finally check the footer of the package END_DATA
            int newpos = firstIndexOf(buf, pos, END_DATA);
            //mismatch, there is no package
            if (newpos != pos) break;
            //increase the packet count
            cnt++;
            //reset the values
            start = pos + END_DATA.length;
            pos = start + START_DATA.length;
            //we only want to verify that we have at least one package
            if ( first ) break;
        }
        return cnt;
    }

    /**
     * Method to check if a package exists in this byte buffer.
     * @return - true if a complete package (header,options,size,data,footer) exists within the buffer
     */
    public boolean doesPackageExist()  {
        return (countPackages(true)>0);
    }

    /**
     * Extracts the message bytes from a package.
     * If no package exists, a IllegalStateException will be thrown.
     * @param clearFromBuffer - if true, the package will be removed from the byte buffer
     * @return - returns the actual message bytes (header, compress,size and footer not included).
     */
    public XByteBuffer extractDataPackage(boolean clearFromBuffer) {
        int psize = countPackages(true);
        if (psize == 0) {
            throw new java.lang.IllegalStateException("No package exists in XByteBuffer");
        }
        int size = toInt(buf, START_DATA.length);
        XByteBuffer xbuf = BufferPool.getBufferPool().getBuffer(size,false);
        xbuf.setLength(size);
        System.arraycopy(buf, START_DATA.length + 4, xbuf.getBytesDirect(), 0, size);
        if (clearFromBuffer) {
            int totalsize = START_DATA.length + 4 + size + END_DATA.length;
            bufSize = bufSize - totalsize;
            System.arraycopy(buf, totalsize, buf, 0, bufSize);
        }
        return xbuf;

    }
    
    public ChannelData extractPackage(boolean clearFromBuffer) throws java.io.IOException {
        XByteBuffer xbuf = extractDataPackage(clearFromBuffer);
        ChannelData cdata = ChannelData.getDataFromPackage(xbuf);
        return cdata;
    }
    
    /**
     * Creates a complete data package
     * @param cdata - the message data to be contained within the package
     * @return - a full package (header,size,data,footer)
     */
    public static byte[] createDataPackage(ChannelData cdata) {
//        return createDataPackage(cdata.getDataPackage());
        //avoid one extra byte array creation
        int dlength = cdata.getDataPackageLength();
        int length = getDataPackageLength(dlength);
        byte[] data = new byte[length];
        int offset = 0;
        System.arraycopy(START_DATA, 0, data, offset, START_DATA.length);
        offset += START_DATA.length;
        toBytes(dlength,data, START_DATA.length);
        offset += 4;
        cdata.getDataPackage(data,offset);
        offset += dlength;
        System.arraycopy(END_DATA, 0, data, offset, END_DATA.length);
        offset += END_DATA.length;
        return data;
    }
    
    public static byte[] createDataPackage(byte[] data, int doff, int dlength, byte[] buffer, int bufoff) {
        if ( (buffer.length-bufoff) > getDataPackageLength(dlength) ) {
            throw new ArrayIndexOutOfBoundsException("Unable to create data package, buffer is too small.");
        }
        System.arraycopy(START_DATA, 0, buffer, bufoff, START_DATA.length);
        toBytes(data.length,buffer, bufoff+START_DATA.length);
        System.arraycopy(data, doff, buffer, bufoff+START_DATA.length + 4, dlength);
        System.arraycopy(END_DATA, 0, buffer, bufoff+START_DATA.length + 4 + data.length, END_DATA.length);
        return buffer;
    }

    
    public static int getDataPackageLength(int datalength) {
        int length = 
            START_DATA.length + //header length
            4 + //data length indicator
            datalength + //actual data length
            END_DATA.length; //footer length
        return length;

    }
    
    public static byte[] createDataPackage(byte[] data) {
        int length = getDataPackageLength(data.length);
        byte[] result = new byte[length];
        return createDataPackage(data,0,data.length,result,0);
    }


//    public static void fillDataPackage(byte[] data, int doff, int dlength, XByteBuffer buf) {
//        int pkglen = getDataPackageLength(dlength);
//        if ( buf.getCapacity() <  pkglen ) buf.expand(pkglen);
//        createDataPackage(data,doff,dlength,buf.getBytesDirect(),buf.getLength());
//    }

    /**
     * Convert four bytes to an int
     * @param b - the byte array containing the four bytes
     * @param off - the offset
     * @return the integer value constructed from the four bytes
     * @exception java.lang.ArrayIndexOutOfBoundsException
     */
    public static int toInt(byte[] b,int off){
        return ( ( b[off+3]) & 0xFF) +
            ( ( ( b[off+2]) & 0xFF) << 8) +
            ( ( ( b[off+1]) & 0xFF) << 16) +
            ( ( ( b[off+0]) & 0xFF) << 24);
    }

    /**
     * Convert eight bytes to a long
     * @param b - the byte array containing the four bytes
     * @param off - the offset
     * @return the long value constructed from the eight bytes
     * @exception java.lang.ArrayIndexOutOfBoundsException
     */
    public static long toLong(byte[] b,int off){
        return ( ( (long) b[off+7]) & 0xFF) +
            ( ( ( (long) b[off+6]) & 0xFF) << 8) +
            ( ( ( (long) b[off+5]) & 0xFF) << 16) +
            ( ( ( (long) b[off+4]) & 0xFF) << 24) +
            ( ( ( (long) b[off+3]) & 0xFF) << 32) +
            ( ( ( (long) b[off+2]) & 0xFF) << 40) +
            ( ( ( (long) b[off+1]) & 0xFF) << 48) +
            ( ( ( (long) b[off+0]) & 0xFF) << 56);
    }

    
    /**
     * Converts a boolean to a 1-byte array
     * @param bool - the integer
     * @return - 1-byte array
     * @deprecated use toBytes(boolean,byte[],int)
     */
    @Deprecated
    public static byte[] toBytes(boolean bool) {
        byte[] b = new byte[1] ;
        return toBytes(bool,b,0);

    }
    
    public static byte[] toBytes(boolean bool, byte[] data, int offset) {
        data[offset] = (byte)(bool?1:0);
        return data;
    }
    
    /**
     * Converts a byte array entry to boolean
     * @param b byte array
     * @param offset within byte array
     * @return true if byte array entry is non-zero, false otherwise
     */
    public static boolean toBoolean(byte[] b, int offset) {
        return b[offset] != 0;
    }

    
    /**
     * Converts an integer to four bytes
     * @param n - the integer
     * @return - four bytes in an array
     * @deprecated use toBytes(int,byte[],int)
     */
    @Deprecated
    public static byte[] toBytes(int n) {
        return toBytes(n,new byte[4],0);
    }

    public static byte[] toBytes(int n,byte[] b, int offset) {
        b[offset+3] = (byte) (n);
        n >>>= 8;
        b[offset+2] = (byte) (n);
        n >>>= 8;
        b[offset+1] = (byte) (n);
        n >>>= 8;
        b[offset+0] = (byte) (n);
        return b;
    }

    /**
     * Converts an long to eight bytes
     * @param n - the long
     * @return - eight bytes in an array
     * @deprecated use toBytes(long,byte[],int)
     */
    @Deprecated
    public static byte[] toBytes(long n) {
        return toBytes(n,new byte[8],0);
    }
    public static byte[] toBytes(long n, byte[] b, int offset) {
        b[offset+7] = (byte) (n);
        n >>>= 8;
        b[offset+6] = (byte) (n);
        n >>>= 8;
        b[offset+5] = (byte) (n);
        n >>>= 8;
        b[offset+4] = (byte) (n);
        n >>>= 8;
        b[offset+3] = (byte) (n);
        n >>>= 8;
        b[offset+2] = (byte) (n);
        n >>>= 8;
        b[offset+1] = (byte) (n);
        n >>>= 8;
        b[offset+0] = (byte) (n);
        return b;
    }

    /**
     * Similar to a String.IndexOf, but uses pure bytes
     * @param src - the source bytes to be searched
     * @param srcOff - offset on the source buffer
     * @param find - the string to be found within src
     * @return - the index of the first matching byte. -1 if the find array is not found
     */
    public static int firstIndexOf(byte[] src, int srcOff, byte[] find){
        int result = -1;
        if (find.length > src.length) return result;
        if (find.length == 0 || src.length == 0) return result;
        if (srcOff >= src.length ) throw new java.lang.ArrayIndexOutOfBoundsException();
        boolean found = false;
        int srclen = src.length;
        int findlen = find.length;
        byte first = find[0];
        int pos = srcOff;
        while (!found) {
            //find the first byte
            while (pos < srclen){
                if (first == src[pos])
                    break;
                pos++;
            }
            if (pos >= srclen)
                return -1;

            //we found the first character
            //match the rest of the bytes - they have to match
            if ( (srclen - pos) < findlen)
                return -1;
            //assume it does exist
            found = true;
            for (int i = 1; ( (i < findlen) && found); i++) {
                found = (find[i] == src[pos + i]);
            }
            if (found) {
                result = pos;
            } else if ( (srclen - pos) < findlen) {
                return -1; //no more matches possible
            } else {
                pos++;
            }
        }
        return result;
    }

    
    public static Serializable deserialize(byte[] data) 
        throws IOException, ClassNotFoundException, ClassCastException {
        return deserialize(data,0,data.length);
    }
    
    public static Serializable deserialize(byte[] data, int offset, int length)  
        throws IOException, ClassNotFoundException, ClassCastException {
        return deserialize(data,offset,length,null);     
    }
    
    private static AtomicInteger invokecount = new AtomicInteger(0);
    
    public static Serializable deserialize(byte[] data, int offset, int length, ClassLoader[] cls) 
        throws IOException, ClassNotFoundException, ClassCastException {
        invokecount.addAndGet(1);
        Object message = null;
        if ( cls == null ) cls = new ClassLoader[0];
        if (data != null && length > 0) {
            InputStream  instream = new ByteArrayInputStream(data,offset,length);
            ObjectInputStream stream = null;
            stream = (cls.length>0)? new ReplicationStream(instream,cls):new ObjectInputStream(instream);
            message = stream.readObject();
            instream.close();
            stream.close();
        }
        if ( message == null ) {
            return null;
        } else if (message instanceof Serializable)
            return (Serializable) message;
        else {
            throw new ClassCastException("Message has the wrong class. It should implement Serializable, instead it is:"+message.getClass().getName());
        }
    }

    /**
     * Serializes a message into cluster data
     * @param msg ClusterMessage
     * @return serialized content as byte[] array 
     * @throws IOException
     */
    public static byte[] serialize(Serializable msg) throws IOException {
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outs);
        out.writeObject(msg);
        out.flush();
        byte[] data = outs.toByteArray();
        return data;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public boolean getDiscard() {
        return discard;
    }

}
