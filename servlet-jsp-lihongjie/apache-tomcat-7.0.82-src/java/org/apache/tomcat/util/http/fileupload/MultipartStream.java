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
package org.apache.tomcat.util.http.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.tomcat.util.http.fileupload.util.Closeable;
import org.apache.tomcat.util.http.fileupload.util.Streams;


/**
 * <p> Low level API for processing file uploads.
 *
 * <p> This class can be used to process data streams conforming to MIME
 * 'multipart' format as defined in
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>. Arbitrarily
 * large amounts of data in the stream can be processed under constant
 * memory usage.
 *
 * <p> The format of the stream is defined in the following way:<br>
 *
 * <code>
 *   multipart-body := preamble 1*encapsulation close-delimiter epilogue<br>
 *   encapsulation := delimiter body CRLF<br>
 *   delimiter := "--" boundary CRLF<br>
 *   close-delimiter := "--" boundary "--"<br>
 *   preamble := &lt;ignore&gt;<br>
 *   epilogue := &lt;ignore&gt;<br>
 *   body := header-part CRLF body-part<br>
 *   header-part := 1*header CRLF<br>
 *   header := header-name ":" header-value<br>
 *   header-name := &lt;printable ascii characters except ":"&gt;<br>
 *   header-value := &lt;any ascii characters except CR &amp; LF&gt;<br>
 *   body-data := &lt;arbitrary data&gt;<br>
 * </code>
 *
 * <p>Note that body-data can contain another mulipart entity.  There
 * is limited support for single pass processing of such nested
 * streams.  The nested stream is <strong>required</strong> to have a
 * boundary token of the same length as the parent stream (see {@link
 * #setBoundary(byte[])}).
 *
 * <p>Here is an example of usage of this class.<br>
 *
 * <pre>
 *   try {
 *     MultipartStream multipartStream = new MultipartStream(input, boundary);
 *     boolean nextPart = multipartStream.skipPreamble();
 *     OutputStream output;
 *     while(nextPart) {
 *       String header = multipartStream.readHeaders();
 *       // process headers
 *       // create some output stream
 *       multipartStream.readBodyData(output);
 *       nextPart = multipartStream.readBoundary();
 *     }
 *   } catch(MultipartStream.MalformedStreamException e) {
 *     // the stream failed to follow required syntax
 *   } catch(IOException e) {
 *     // a read or write error occurred
 *   }
 * </pre>
 */
public class MultipartStream {

    /**
     * Internal class, which is used to invoke the
     * {@link ProgressListener}.
     */
    public static class ProgressNotifier {
        /**
         * The listener to invoke.
         */
        private final ProgressListener listener;

        /**
         * Number of expected bytes, if known, or -1.
         */
        private final long contentLength;

        /**
         * Number of bytes, which have been read so far.
         */
        private long bytesRead;

        /**
         * Number of items, which have been read so far.
         */
        private int items;

        /**
         * Creates a new instance with the given listener
         * and content length.
         *
         * @param pListener The listener to invoke.
         * @param pContentLength The expected content length.
         */
        ProgressNotifier(ProgressListener pListener, long pContentLength) {
            listener = pListener;
            contentLength = pContentLength;
        }

        /**
         * Called to indicate that bytes have been read.
         *
         * @param pBytes Number of bytes, which have been read.
         */
        void noteBytesRead(int pBytes) {
            /* Indicates, that the given number of bytes have been read from
             * the input stream.
             */
            bytesRead += pBytes;
            notifyListener();
        }

        /**
         * Called to indicate, that a new file item has been detected.
         */
        void noteItem() {
            ++items;
            notifyListener();
        }

        /**
         * Called for notifying the listener.
         */
        private void notifyListener() {
            if (listener != null) {
                listener.update(bytesRead, contentLength, items);
            }
        }

    }

    // ----------------------------------------------------- Manifest constants

    /**
     * The Carriage Return ASCII character value.
     */
    public static final byte CR = 0x0D;

    /**
     * The Line Feed ASCII character value.
     */
    public static final byte LF = 0x0A;

    /**
     * The dash (-) ASCII character value.
     */
    public static final byte DASH = 0x2D;

    /**
     * The maximum length of <code>header-part</code> that will be
     * processed (10 kilobytes = 10240 bytes.).
     */
    public static final int HEADER_PART_SIZE_MAX = 10240;

    /**
     * The default length of the buffer used for processing a request.
     */
    protected static final int DEFAULT_BUFSIZE = 4096;

    /**
     * A byte sequence that marks the end of <code>header-part</code>
     * (<code>CRLFCRLF</code>).
     */
    protected static final byte[] HEADER_SEPARATOR = {CR, LF, CR, LF};

    /**
     * A byte sequence that that follows a delimiter that will be
     * followed by an encapsulation (<code>CRLF</code>).
     */
    protected static final byte[] FIELD_SEPARATOR = {CR, LF};

    /**
     * A byte sequence that that follows a delimiter of the last
     * encapsulation in the stream (<code>--</code>).
     */
    protected static final byte[] STREAM_TERMINATOR = {DASH, DASH};

    /**
     * A byte sequence that precedes a boundary (<code>CRLF--</code>).
     */
    protected static final byte[] BOUNDARY_PREFIX = {CR, LF, DASH, DASH};

    // ----------------------------------------------------------- Data members

    /**
     * The input stream from which data is read.
     */
    private final InputStream input;

    /**
     * The length of the boundary token plus the leading <code>CRLF--</code>.
     */
    private int boundaryLength;

    /**
     * The amount of data, in bytes, that must be kept in the buffer in order
     * to detect delimiters reliably.
     */
    private final int keepRegion;

    /**
     * The byte sequence that partitions the stream.
     */
    private final byte[] boundary;

    /**
     * The table for Knuth-Morris-Pratt search algorithm
     */
    private int[] boundaryTable;

    /**
     * The length of the buffer used for processing the request.
     */
    private final int bufSize;

    /**
     * The buffer used for processing the request.
     */
    private final byte[] buffer;

    /**
     * The index of first valid character in the buffer.
     * <br>
     * 0 <= head < bufSize
     */
    private int head;

    /**
     * The index of last valid character in the buffer + 1.
     * <br>
     * 0 <= tail <= bufSize
     */
    private int tail;

    /**
     * The content encoding to use when reading headers.
     */
    private String headerEncoding;

    /**
     * The progress notifier, if any, or null.
     */
    private final ProgressNotifier notifier;

    // ----------------------------------------------------------- Constructors

    /**
     * <p> Constructs a <code>MultipartStream</code> with a custom size buffer.
     *
     * <p> Note that the buffer must be at least big enough to contain the
     * boundary string, plus 4 characters for CR/LF and double dash, plus at
     * least one byte of data.  Too small a buffer size setting will degrade
     * performance.
     *
     * @param input    The <code>InputStream</code> to serve as a data source.
     * @param boundary The token used for dividing the stream into
     *                 <code>encapsulations</code>.
     * @param bufSize  The size of the buffer to be used, in bytes.
     * @param pNotifier The notifier, which is used for calling the
     *                  progress listener, if any.
     *
     * @throws IllegalArgumentException If the buffer size is too small
     *
     * @since 1.3.1
     */
    public MultipartStream(InputStream input,
            byte[] boundary,
            int bufSize,
            ProgressNotifier pNotifier) {

        if (boundary == null) {
            throw new IllegalArgumentException("boundary may not be null");
        }
        // We prepend CR/LF to the boundary to chop trailing CR/LF from
        // body-data tokens.
        this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;
        if (bufSize < this.boundaryLength + 1) {
            throw new IllegalArgumentException(
                    "The buffer size specified for the MultipartStream is too small");
        }

        this.input = input;
        this.bufSize = Math.max(bufSize, boundaryLength*2);
        this.buffer = new byte[this.bufSize];
        this.notifier = pNotifier;

        this.boundary = new byte[this.boundaryLength];
        this.boundaryTable = new int[this.boundaryLength + 1];
        this.keepRegion = this.boundary.length;

        System.arraycopy(BOUNDARY_PREFIX, 0, this.boundary, 0,
                BOUNDARY_PREFIX.length);
        System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length,
                boundary.length);
        computeBoundaryTable();

        head = 0;
        tail = 0;
    }

    /**
     * <p> Constructs a <code>MultipartStream</code> with a default size buffer.
     *
     * @param input    The <code>InputStream</code> to serve as a data source.
     * @param boundary The token used for dividing the stream into
     *                 <code>encapsulations</code>.
     * @param pNotifier An object for calling the progress listener, if any.
     *
     *
     * @see #MultipartStream(InputStream, byte[], int, ProgressNotifier)
     */
    MultipartStream(InputStream input,
            byte[] boundary,
            ProgressNotifier pNotifier) {
        this(input, boundary, DEFAULT_BUFSIZE, pNotifier);
    }

    // --------------------------------------------------------- Public methods

    /**
     * Retrieves the character encoding used when reading the headers of an
     * individual part. When not specified, or <code>null</code>, the platform
     * default encoding is used.
     *
     * @return The encoding used to read part headers.
     */
    public String getHeaderEncoding() {
        return headerEncoding;
    }

    /**
     * Specifies the character encoding to be used when reading the headers of
     * individual parts. When not specified, or <code>null</code>, the platform
     * default encoding is used.
     *
     * @param encoding The encoding used to read part headers.
     */
    public void setHeaderEncoding(String encoding) {
        headerEncoding = encoding;
    }

    /**
     * Reads a byte from the <code>buffer</code>, and refills it as
     * necessary.
     *
     * @return The next byte from the input stream.
     *
     * @throws IOException if there is no more data available.
     */
    public byte readByte() throws IOException {
        // Buffer depleted ?
        if (head == tail) {
            head = 0;
            // Refill.
            tail = input.read(buffer, head, bufSize);
            if (tail == -1) {
                // No more data available.
                throw new IOException("No more data is available");
            }
            if (notifier != null) {
                notifier.noteBytesRead(tail);
            }
        }
        return buffer[head++];
    }

    /**
     * Skips a <code>boundary</code> token, and checks whether more
     * <code>encapsulations</code> are contained in the stream.
     *
     * @return <code>true</code> if there are more encapsulations in
     *         this stream; <code>false</code> otherwise.
     *
     * @throws FileUploadIOException if the bytes read from the stream exceeded the size limits
     * @throws MalformedStreamException if the stream ends unexpectedly or
     *                                  fails to follow required syntax.
     */
    public boolean readBoundary()
            throws FileUploadIOException, MalformedStreamException {
        byte[] marker = new byte[2];
        boolean nextChunk = false;

        head += boundaryLength;
        try {
            marker[0] = readByte();
            if (marker[0] == LF) {
                // Work around IE5 Mac bug with input type=image.
                // Because the boundary delimiter, not including the trailing
                // CRLF, must not appear within any file (RFC 2046, section
                // 5.1.1), we know the missing CR is due to a buggy browser
                // rather than a file containing something similar to a
                // boundary.
                return true;
            }

            marker[1] = readByte();
            if (arrayequals(marker, STREAM_TERMINATOR, 2)) {
                nextChunk = false;
            } else if (arrayequals(marker, FIELD_SEPARATOR, 2)) {
                nextChunk = true;
            } else {
                throw new MalformedStreamException(
                "Unexpected characters follow a boundary");
            }
        } catch (FileUploadIOException e) {
            // wraps a SizeException, re-throw as it will be unwrapped later
            throw e;
        } catch (IOException e) {
            throw new MalformedStreamException("Stream ended unexpectedly");
        }
        return nextChunk;
    }

    /**
     * <p>Changes the boundary token used for partitioning the stream.
     *
     * <p>This method allows single pass processing of nested multipart
     * streams.
     *
     * <p>The boundary token of the nested stream is <code>required</code>
     * to be of the same length as the boundary token in parent stream.
     *
     * <p>Restoring the parent stream boundary token after processing of a
     * nested stream is left to the application.
     *
     * @param boundary The boundary to be used for parsing of the nested
     *                 stream.
     *
     * @throws IllegalBoundaryException if the <code>boundary</code>
     *                                  has a different length than the one
     *                                  being currently parsed.
     */
    public void setBoundary(byte[] boundary)
            throws IllegalBoundaryException {
        if (boundary.length != boundaryLength - BOUNDARY_PREFIX.length) {
            throw new IllegalBoundaryException(
            "The length of a boundary token cannot be changed");
        }
        System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length,
                boundary.length);
        computeBoundaryTable();
    }

    /**
     * Compute the table used for Knuth-Morris-Pratt search algorithm.
     */
    private void computeBoundaryTable() {
        int position = 2;
        int candidate = 0;

        boundaryTable[0] = -1;
        boundaryTable[1] = 0;

        while (position <= boundaryLength) {
            if (boundary[position - 1] == boundary[candidate]) {
                boundaryTable[position] = candidate + 1;
                candidate++;
                position++;
            } else if (candidate > 0) {
                candidate = boundaryTable[candidate];
            } else {
                boundaryTable[position] = 0;
                position++;
            }
        }
    }

    /**
     * <p>Reads the <code>header-part</code> of the current
     * <code>encapsulation</code>.
     *
     * <p>Headers are returned verbatim to the input stream, including the
     * trailing <code>CRLF</code> marker. Parsing is left to the
     * application.
     *
     * <p><strong>TODO</strong> allow limiting maximum header size to
     * protect against abuse.
     *
     * @return The <code>header-part</code> of the current encapsulation.
     *
     * @throws FileUploadIOException if the bytes read from the stream exceeded the size limits.
     * @throws MalformedStreamException if the stream ends unexpectedly.
     */
    public String readHeaders() throws FileUploadIOException, MalformedStreamException {
        int i = 0;
        byte b;
        // to support multi-byte characters
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int size = 0;
        while (i < HEADER_SEPARATOR.length) {
            try {
                b = readByte();
            } catch (FileUploadIOException e) {
                // wraps a SizeException, re-throw as it will be unwrapped later
                throw e;
            } catch (IOException e) {
                throw new MalformedStreamException("Stream ended unexpectedly");
            }
            if (++size > HEADER_PART_SIZE_MAX) {
                throw new MalformedStreamException(String.format(
                        "Header section has more than %s bytes (maybe it is not properly terminated)",
                        Integer.valueOf(HEADER_PART_SIZE_MAX)));
            }
            if (b == HEADER_SEPARATOR[i]) {
                i++;
            } else {
                i = 0;
            }
            baos.write(b);
        }

        String headers = null;
        if (headerEncoding != null) {
            try {
                headers = baos.toString(headerEncoding);
            } catch (UnsupportedEncodingException e) {
                // Fall back to platform default if specified encoding is not
                // supported.
                headers = baos.toString();
            }
        } else {
            headers = baos.toString();
        }

        return headers;
    }

    /**
     * <p>Reads <code>body-data</code> from the current
     * <code>encapsulation</code> and writes its contents into the
     * output <code>Stream</code>.
     *
     * <p>Arbitrary large amounts of data can be processed by this
     * method using a constant size buffer. (see {@link
     * #MultipartStream(InputStream,byte[],int,
     *   MultipartStream.ProgressNotifier) constructor}).
     *
     * @param output The <code>Stream</code> to write data into. May
     *               be null, in which case this method is equivalent
     *               to {@link #discardBodyData()}.
     *
     * @return the amount of data written.
     *
     * @throws MalformedStreamException if the stream ends unexpectedly.
     * @throws IOException              if an i/o error occurs.
     */
    public int readBodyData(OutputStream output)
            throws MalformedStreamException, IOException {
        return (int) Streams.copy(newInputStream(), output, false); // N.B. Streams.copy closes the input stream
    }

    /**
     * Creates a new {@link ItemInputStream}.
     * @return A new instance of {@link ItemInputStream}.
     */
    ItemInputStream newInputStream() {
        return new ItemInputStream();
    }

    /**
     * <p> Reads <code>body-data</code> from the current
     * <code>encapsulation</code> and discards it.
     *
     * <p>Use this method to skip encapsulations you don't need or don't
     * understand.
     *
     * @return The amount of data discarded.
     *
     * @throws MalformedStreamException if the stream ends unexpectedly.
     * @throws IOException              if an i/o error occurs.
     */
    public int discardBodyData() throws MalformedStreamException, IOException {
        return readBodyData(null);
    }

    /**
     * Finds the beginning of the first <code>encapsulation</code>.
     *
     * @return <code>true</code> if an <code>encapsulation</code> was found in
     *         the stream.
     *
     * @throws IOException if an i/o error occurs.
     */
    public boolean skipPreamble() throws IOException {
        // First delimiter may be not preceeded with a CRLF.
        System.arraycopy(boundary, 2, boundary, 0, boundary.length - 2);
        boundaryLength = boundary.length - 2;
        computeBoundaryTable();
        try {
            // Discard all data up to the delimiter.
            discardBodyData();

            // Read boundary - if succeeded, the stream contains an
            // encapsulation.
            return readBoundary();
        } catch (MalformedStreamException e) {
            return false;
        } finally {
            // Restore delimiter.
            System.arraycopy(boundary, 0, boundary, 2, boundary.length - 2);
            boundaryLength = boundary.length;
            boundary[0] = CR;
            boundary[1] = LF;
            computeBoundaryTable();
        }
    }

    /**
     * Compares <code>count</code> first bytes in the arrays
     * <code>a</code> and <code>b</code>.
     *
     * @param a     The first array to compare.
     * @param b     The second array to compare.
     * @param count How many bytes should be compared.
     *
     * @return <code>true</code> if <code>count</code> first bytes in arrays
     *         <code>a</code> and <code>b</code> are equal.
     */
    public static boolean arrayequals(byte[] a,
            byte[] b,
            int count) {
        for (int i = 0; i < count; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Searches for a byte of specified value in the <code>buffer</code>,
     * starting at the specified <code>position</code>.
     *
     * @param value The value to find.
     * @param pos   The starting position for searching.
     *
     * @return The position of byte found, counting from beginning of the
     *         <code>buffer</code>, or <code>-1</code> if not found.
     */
    protected int findByte(byte value,
            int pos) {
        for (int i = pos; i < tail; i++) {
            if (buffer[i] == value) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searches for the <code>boundary</code> in the <code>buffer</code>
     * region delimited by <code>head</code> and <code>tail</code>.
     *
     * @return The position of the boundary found, counting from the
     *         beginning of the <code>buffer</code>, or <code>-1</code> if
     *         not found.
     */
    protected int findSeparator() {

        int bufferPos = this.head;
        int tablePos = 0;

        while (bufferPos < this.tail) {
            while (tablePos >= 0 && buffer[bufferPos] != boundary[tablePos]) {
                tablePos = boundaryTable[tablePos];
            }
            bufferPos++;
            tablePos++;
            if (tablePos == boundaryLength) {
                return bufferPos - boundaryLength;
            }
        }
        return -1;
    }

    /**
     * Thrown to indicate that the input stream fails to follow the
     * required syntax.
     */
    public static class MalformedStreamException extends IOException {

        /**
         * The UID to use when serializing this instance.
         */
        private static final long serialVersionUID = 6466926458059796677L;

        /**
         * Constructs a <code>MalformedStreamException</code> with no
         * detail message.
         */
        public MalformedStreamException() {
            super();
        }

        /**
         * Constructs an <code>MalformedStreamException</code> with
         * the specified detail message.
         *
         * @param message The detail message.
         */
        public MalformedStreamException(String message) {
            super(message);
        }

    }

    /**
     * Thrown upon attempt of setting an invalid boundary token.
     */
    public static class IllegalBoundaryException extends IOException {


        /**
         * The UID to use when serializing this instance.
         */
        private static final long serialVersionUID = -161533165102632918L;

        /**
         * Constructs an <code>IllegalBoundaryException</code> with no
         * detail message.
         */
        public IllegalBoundaryException() {
            super();
        }

        /**
         * Constructs an <code>IllegalBoundaryException</code> with
         * the specified detail message.
         *
         * @param message The detail message.
         */
        public IllegalBoundaryException(String message) {
            super(message);
        }

    }

    /**
     * An {@link InputStream} for reading an items contents.
     */
    public class ItemInputStream extends InputStream implements Closeable {

        /**
         * The number of bytes, which have been read so far.
         */
        private long total;

        /**
         * The number of bytes, which must be hold, because
         * they might be a part of the boundary.
         */
        private int pad;

        /**
         * The current offset in the buffer.
         */
        private int pos;

        /**
         * Whether the stream is already closed.
         */
        private boolean closed;

        /**
         * Creates a new instance.
         */
        ItemInputStream() {
            findSeparator();
        }

        /**
         * Called for finding the separator.
         */
        private void findSeparator() {
            pos = MultipartStream.this.findSeparator();
            if (pos == -1) {
                if (tail - head > keepRegion) {
                    pad = keepRegion;
                } else {
                    pad = tail - head;
                }
            }
        }

        /**
         * Returns the number of bytes, which have been read
         * by the stream.
         *
         * @return Number of bytes, which have been read so far.
         */
        public long getBytesRead() {
            return total;
        }

        /**
         * Returns the number of bytes, which are currently
         * available, without blocking.
         *
         * @throws IOException An I/O error occurs.
         * @return Number of bytes in the buffer.
         */
        @Override
        public int available() throws IOException {
            if (pos == -1) {
                return tail - head - pad;
            }
            return pos - head;
        }

        /**
         * Offset when converting negative bytes to integers.
         */
        private static final int BYTE_POSITIVE_OFFSET = 256;

        /**
         * Returns the next byte in the stream.
         *
         * @return The next byte in the stream, as a non-negative
         *   integer, or -1 for EOF.
         * @throws IOException An I/O error occurred.
         */
        @Override
        public int read() throws IOException {
            if (closed) {
                throw new FileItemStream.ItemSkippedException();
            }
            if (available() == 0 && makeAvailable() == 0) {
                return -1;
            }
            ++total;
            int b = buffer[head++];
            if (b >= 0) {
                return b;
            }
            return b + BYTE_POSITIVE_OFFSET;
        }

        /**
         * Reads bytes into the given buffer.
         *
         * @param b The destination buffer, where to write to.
         * @param off Offset of the first byte in the buffer.
         * @param len Maximum number of bytes to read.
         * @return Number of bytes, which have been actually read,
         *   or -1 for EOF.
         * @throws IOException An I/O error occurred.
         */
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (closed) {
                throw new FileItemStream.ItemSkippedException();
            }
            if (len == 0) {
                return 0;
            }
            int res = available();
            if (res == 0) {
                res = makeAvailable();
                if (res == 0) {
                    return -1;
                }
            }
            res = Math.min(res, len);
            System.arraycopy(buffer, head, b, off, res);
            head += res;
            total += res;
            return res;
        }

        /**
         * Closes the input stream.
         *
         * @throws IOException An I/O error occurred.
         */
        @Override
        public void close() throws IOException {
            close(false);
        }

        /**
         * Closes the input stream.
         *
         * @param pCloseUnderlying Whether to close the underlying stream
         *   (hard close)
         * @throws IOException An I/O error occurred.
         */
        public void close(boolean pCloseUnderlying) throws IOException {
            if (closed) {
                return;
            }
            if (pCloseUnderlying) {
                closed = true;
                input.close();
            } else {
                for (;;) {
                    int av = available();
                    if (av == 0) {
                        av = makeAvailable();
                        if (av == 0) {
                            break;
                        }
                    }
                    skip(av);
                }
            }
            closed = true;
        }

        /**
         * Skips the given number of bytes.
         *
         * @param bytes Number of bytes to skip.
         * @return The number of bytes, which have actually been
         *   skipped.
         * @throws IOException An I/O error occurred.
         */
        @Override
        public long skip(long bytes) throws IOException {
            if (closed) {
                throw new FileItemStream.ItemSkippedException();
            }
            int av = available();
            if (av == 0) {
                av = makeAvailable();
                if (av == 0) {
                    return 0;
                }
            }
            long res = Math.min(av, bytes);
            head += res;
            return res;
        }

        /**
         * Attempts to read more data.
         *
         * @return Number of available bytes
         * @throws IOException An I/O error occurred.
         */
        private int makeAvailable() throws IOException {
            if (pos != -1) {
                return 0;
            }

            // Move the data to the beginning of the buffer.
            total += tail - head - pad;
            System.arraycopy(buffer, tail - pad, buffer, 0, pad);

            // Refill buffer with new data.
            head = 0;
            tail = pad;

            for (;;) {
                int bytesRead = input.read(buffer, tail, bufSize - tail);
                if (bytesRead == -1) {
                    // The last pad amount is left in the buffer.
                    // Boundary can't be in there so signal an error
                    // condition.
                    final String msg = "Stream ended unexpectedly";
                    throw new MalformedStreamException(msg);
                }
                if (notifier != null) {
                    notifier.noteBytesRead(bytesRead);
                }
                tail += bytesRead;

                findSeparator();
                int av = available();

                if (av > 0 || pos != -1) {
                    return av;
                }
            }
        }

        /**
         * Returns, whether the stream is closed.
         *
         * @return True, if the stream is closed, otherwise false.
         */
        @Override
        public boolean isClosed() {
            return closed;
        }

    }

}
