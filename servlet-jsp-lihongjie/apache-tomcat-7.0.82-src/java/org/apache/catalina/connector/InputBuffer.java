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
package org.apache.catalina.connector;

import java.io.IOException;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.security.SecurityUtil;
import org.apache.coyote.ActionCode;
import org.apache.coyote.Request;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.res.StringManager;


/**
 * The buffer used by Tomcat request. This is a derivative of the Tomcat 3.3
 * OutputBuffer, adapted to handle input instead of output. This allows
 * complete recycling of the facade objects (the ServletInputStream and the
 * BufferedReader).
 *
 * @author Remy Maucherat
 */
public class InputBuffer extends Reader
    implements ByteChunk.ByteInputChannel, CharChunk.CharInputChannel,
               CharChunk.CharOutputChannel {

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    // -------------------------------------------------------------- Constants


    public static final String DEFAULT_ENCODING =
        org.apache.coyote.Constants.DEFAULT_CHARACTER_ENCODING;
    public static final int DEFAULT_BUFFER_SIZE = 8*1024;

    // The buffer can be used for byte[] and char[] reading
    // ( this is needed to support ServletInputStream and BufferedReader )
    public final int INITIAL_STATE = 0;
    public final int CHAR_STATE = 1;
    public final int BYTE_STATE = 2;

    // ----------------------------------------------------- Instance Variables


    /**
     * The byte buffer.
     */
    private final ByteChunk bb;


    /**
     * The chunk buffer.
     */
    private CharChunk cb;


    /**
     * State of the output buffer.
     */
    private int state = 0;


    /**
     * Flag which indicates if the input buffer is closed.
     */
    private boolean closed = false;


    /**
     * Encoding to use.
     */
    private String enc;


    /**
     * Encoder is set.
     */
    private boolean gotEnc = false;


    /**
     * List of encoders.
     */
    private final Map<String,B2CConverter> encoders = new ConcurrentHashMap<String, B2CConverter>();


    /**
     * Current byte to char converter.
     */
    protected B2CConverter conv;


    /**
     * Associated Coyote request.
     */
    private Request coyoteRequest;


    /**
     * Buffer position.
     */
    private int markPos = -1;


    /**
     * Buffer size.
     */
    private int size = -1;


    // ----------------------------------------------------------- Constructors


    /**
     * Default constructor. Allocate the buffer with the default buffer size.
     */
    public InputBuffer() {

        this(DEFAULT_BUFFER_SIZE);

    }


    /**
     * Alternate constructor which allows specifying the initial buffer size.
     *
     * @param size Buffer size to use
     */
    public InputBuffer(int size) {

        this.size = size;
        bb = new ByteChunk(size);
        bb.setLimit(size);
        bb.setByteInputChannel(this);
        cb = new CharChunk(size);
        cb.setLimit(size);
        cb.setOptimizedWrite(false);
        cb.setCharInputChannel(this);
        cb.setCharOutputChannel(this);

    }


    // ------------------------------------------------------------- Properties


    /**
     * Associated Coyote request.
     *
     * @param coyoteRequest Associated Coyote request
     */
    public void setRequest(Request coyoteRequest) {
        this.coyoteRequest = coyoteRequest;
    }


    /**
     * Get associated Coyote request.
     *
     * @return the associated Coyote request
     */
    @Deprecated
    public Request getRequest() {
        return this.coyoteRequest;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Recycle the output buffer.
     */
    public void recycle() {

        state = INITIAL_STATE;

        // If usage of mark made the buffer too big, reallocate it
        if (cb.getChars().length > size) {
            cb = new CharChunk(size);
            cb.setLimit(size);
            cb.setOptimizedWrite(false);
            cb.setCharInputChannel(this);
            cb.setCharOutputChannel(this);
        } else {
            cb.recycle();
        }
        markPos = -1;
        bb.recycle();
        closed = false;

        if (conv != null) {
            conv.recycle();
        }

        gotEnc = false;
        enc = null;

    }


    /**
     * Clear cached encoders (to save memory for Comet requests).
     */
    public void clearEncoders() {
        encoders.clear();
    }


    /**
     * Close the input buffer.
     *
     * @throws IOException An underlying IOException occurred
     */
    @Override
    public void close()
        throws IOException {
        closed = true;
    }


    public int available() {
        int available = 0;
        if (state == BYTE_STATE) {
            available = bb.getLength();
        } else if (state == CHAR_STATE) {
            available = cb.getLength();
        }
        if (available == 0) {
            coyoteRequest.action(ActionCode.AVAILABLE, null);
            available = (coyoteRequest.getAvailable() > 0) ? 1 : 0;
        }
        return available;
    }


    // ------------------------------------------------- Bytes Handling Methods


    /**
     * Reads new bytes in the byte chunk.
     *
     * @param cbuf Byte buffer to be written to the response
     * @param off Offset
     * @param len Length
     *
     * @throws IOException An underlying IOException occurred
     */
    @Override
    public int realReadBytes(byte cbuf[], int off, int len)
            throws IOException {

        if (closed) {
            return -1;
        }
        if (coyoteRequest == null) {
            return -1;
        }

        if(state == INITIAL_STATE) {
            state = BYTE_STATE;
        }

        int result = coyoteRequest.doRead(bb);

        return result;

    }


    public int readByte()
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return bb.substract();
    }


    public int read(byte[] b, int off, int len)
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return bb.substract(b, off, len);
    }


    // ------------------------------------------------- Chars Handling Methods


    /**
     * Since the converter will use append, it is possible to get chars to
     * be removed from the buffer for "writing". Since the chars have already
     * been read before, they are ignored. If a mark was set, then the
     * mark is lost.
     */
    @Override
    public void realWriteChars(char c[], int off, int len)
        throws IOException {
        markPos = -1;
        cb.setOffset(0);
        cb.setEnd(0);
    }


    public void setEncoding(String s) {
        enc = s;
    }


    @Override
    public int realReadChars(char cbuf[], int off, int len)
        throws IOException {

        if (!gotEnc) {
            setConverter();
        }

        boolean eof = false;

        if (bb.getLength() <= 0) {
            int nRead = realReadBytes(bb.getBytes(), 0, bb.getBytes().length);
            if (nRead < 0) {
                eof = true;
            }
        }

        if (markPos == -1) {
            cb.setOffset(0);
            cb.setEnd(0);
        } else {
            // Make sure there's enough space in the worst case
            cb.makeSpace(bb.getLength());
            if ((cb.getBuffer().length - cb.getEnd()) == 0 && bb.getLength() != 0) {
                // We went over the limit
                cb.setOffset(0);
                cb.setEnd(0);
                markPos = -1;
            }
        }

        state = CHAR_STATE;
        conv.convert(bb, cb, eof);

        if (cb.getLength() == 0 && eof) {
            return -1;
        } else {
            return cb.getLength();
        }
    }


    @Override
    public int read()
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return cb.substract();
    }


    @Override
    public int read(char[] cbuf)
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return read(cbuf, 0, cbuf.length);
    }


    @Override
    public int read(char[] cbuf, int off, int len)
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return cb.substract(cbuf, off, len);
    }


    @Override
    public long skip(long n)
        throws IOException {


        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        if (n < 0) {
            throw new IllegalArgumentException();
        }

        long nRead = 0;
        while (nRead < n) {
            if (cb.getLength() >= n) {
                cb.setOffset(cb.getStart() + (int) n);
                nRead = n;
            } else {
                nRead += cb.getLength();
                cb.setOffset(cb.getEnd());
                int toRead = 0;
                if (cb.getChars().length < (n - nRead)) {
                    toRead = cb.getChars().length;
                } else {
                    toRead = (int) (n - nRead);
                }
                int nb = realReadChars(cb.getChars(), 0, toRead);
                if (nb < 0) {
                    break;
                }
            }
        }

        return nRead;

    }


    @Override
    public boolean ready()
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        return (available() > 0);
    }


    @Override
    public boolean markSupported() {
        return true;
    }


    @Override
    public void mark(int readAheadLimit)
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        if (cb.getLength() <= 0) {
            cb.setOffset(0);
            cb.setEnd(0);
        } else {
            if ((cb.getBuffer().length > (2 * size))
                && (cb.getLength()) < (cb.getStart())) {
                System.arraycopy(cb.getBuffer(), cb.getStart(),
                                 cb.getBuffer(), 0, cb.getLength());
                cb.setEnd(cb.getLength());
                cb.setOffset(0);
            }
        }
        cb.setLimit(cb.getStart() + readAheadLimit + size);
        markPos = cb.getStart();
    }


    @Override
    public void reset()
        throws IOException {

        if (closed) {
            throw new IOException(sm.getString("inputBuffer.streamClosed"));
        }

        if (state == CHAR_STATE) {
            if (markPos < 0) {
                cb.recycle();
                markPos = -1;
                throw new IOException();
            } else {
                cb.setOffset(markPos);
            }
        } else {
            bb.recycle();
        }
    }


    public void checkConverter()
        throws IOException {

        if (!gotEnc) {
            setConverter();
        }

    }


    protected void setConverter()
        throws IOException {

        if (coyoteRequest != null) {
            enc = coyoteRequest.getCharacterEncoding();
        }

        gotEnc = true;
        if (enc == null) {
            enc = DEFAULT_ENCODING;
        }
        conv = encoders.get(enc);
        if (conv == null) {
            if (SecurityUtil.isPackageProtectionEnabled()){
                try{
                    conv = AccessController.doPrivileged(
                            new PrivilegedExceptionAction<B2CConverter>(){

                                @Override
                                public B2CConverter run() throws IOException {
                                    return new B2CConverter(enc);
                                }

                            }
                    );
                } catch (PrivilegedActionException ex){
                    Exception e = ex.getException();
                    if (e instanceof IOException) {
                        throw (IOException)e;
                    } else {
                        throw new IOException(e);
                    }
                }
            } else {
                conv = new B2CConverter(enc);
            }
            encoders.put(enc, conv);
        }

    }

}
