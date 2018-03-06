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

package org.apache.tomcat.jni;

/* Import needed classes */
import java.nio.ByteBuffer;

/** Socket
 *
 * @author Mladen Turk
 */
public class Socket {

    /* Standard socket defines */
    public static final int SOCK_STREAM = 0;
    public static final int SOCK_DGRAM  = 1;
    /*
     * apr_sockopt Socket option definitions
     */
    public static final int APR_SO_LINGER       = 1;    /** Linger */
    public static final int APR_SO_KEEPALIVE    = 2;    /** Keepalive */
    public static final int APR_SO_DEBUG        = 4;    /** Debug */
    public static final int APR_SO_NONBLOCK     = 8;    /** Non-blocking IO */
    public static final int APR_SO_REUSEADDR    = 16;   /** Reuse addresses */
    public static final int APR_SO_SNDBUF       = 64;   /** Send buffer */
    public static final int APR_SO_RCVBUF       = 128;  /** Receive buffer */
    public static final int APR_SO_DISCONNECTED = 256;  /** Disconnected */
    /** For SCTP sockets, this is mapped to STCP_NODELAY internally. */
    public static final int APR_TCP_NODELAY     = 512;
    public static final int APR_TCP_NOPUSH      = 1024; /** No push */
    /** This flag is ONLY set internally when we set APR_TCP_NOPUSH with
     * APR_TCP_NODELAY set to tell us that APR_TCP_NODELAY should be turned on
     * again when NOPUSH is turned off
     */
    public static final int APR_RESET_NODELAY   = 2048;
    /** Set on non-blocking sockets (timeout != 0) on which the
     * previous read() did not fill a buffer completely.  the next
     * apr_socket_recv()  will first call select()/poll() rather than
     * going straight into read().  (Can also be set by an application to
     * force a select()/poll() call before the next read, in cases where
     * the app expects that an immediate read would fail.)
     */
    public static final int APR_INCOMPLETE_READ = 4096;
    /** like APR_INCOMPLETE_READ, but for write
     */
    public static final int APR_INCOMPLETE_WRITE = 8192;
    /** Don't accept IPv4 connections on an IPv6 listening socket.
     */
    public static final int APR_IPV6_V6ONLY      = 16384;
    /** Delay accepting of new connections until data is available.
     */
    public static final int APR_TCP_DEFER_ACCEPT = 32768;

    /** Define what type of socket shutdown should occur.
     * apr_shutdown_how_e enum
     */
    public static final int APR_SHUTDOWN_READ      = 0; /** no longer allow read request */
    public static final int APR_SHUTDOWN_WRITE     = 1; /** no longer allow write requests */
    public static final int APR_SHUTDOWN_READWRITE = 2; /** no longer allow read or write requests */

    public static final int APR_IPV4_ADDR_OK = 0x01;
    public static final int APR_IPV6_ADDR_OK = 0x02;

    /* TODO: Missing:
     * APR_INET
     * APR_UNSPEC
     * APR_INET6
     */
    public static final int APR_UNSPEC = 0;
    public static final int APR_INET   = 1;
    public static final int APR_INET6  = 2;

    public static final int APR_PROTO_TCP  =   6; /** TCP  */
    public static final int APR_PROTO_UDP  =  17; /** UDP  */
    public static final int APR_PROTO_SCTP = 132; /** SCTP */

    /**
     * Enum to tell us if we're interested in remote or local socket
     * apr_interface_e
     */
    public static final int APR_LOCAL  = 0;
    public static final int APR_REMOTE = 1;

    /* Socket.get types */
    public static final int SOCKET_GET_POOL = 0;
    public static final int SOCKET_GET_IMPL = 1;
    public static final int SOCKET_GET_APRS = 2;
    public static final int SOCKET_GET_TYPE = 3;

    /**
     * Create a socket.
     * @param family The address family of the socket (e.g., APR_INET).
     * @param type The type of the socket (e.g., SOCK_STREAM).
     * @param protocol The protocol of the socket (e.g., APR_PROTO_TCP).
     * @param cont The parent pool to use
     * @return The new socket that has been set up.
     */
    public static native long create(int family, int type,
                                     int protocol, long cont)
        throws Exception;


    /**
     * Shutdown either reading, writing, or both sides of a socket.
     * <br>
     * This does not actually close the socket descriptor, it just
     *      controls which calls are still valid on the socket.
     * @param thesocket The socket to close
     * @param how How to shutdown the socket.  One of:
     * <PRE>
     * APR_SHUTDOWN_READ         no longer allow read requests
     * APR_SHUTDOWN_WRITE        no longer allow write requests
     * APR_SHUTDOWN_READWRITE    no longer allow read or write requests
     * </PRE>
     */
    public static native int shutdown(long thesocket, int how);

    /**
     * Close a socket.
     * @param thesocket The socket to close
     */
    public static native int close(long thesocket);

    /**
     * Destroy a pool associated with socket
     * @param thesocket The destroy
     */
    public static native void destroy(long thesocket);

    /**
     * Bind the socket to its associated port
     * @param sock The socket to bind
     * @param sa The socket address to bind to
     * This may be where we will find out if there is any other process
     *      using the selected port.
     */
    public static native int bind(long sock, long sa);

    /**
     * Listen to a bound socket for connections.
     * @param sock The socket to listen on
     * @param backlog The number of outstanding connections allowed in the sockets
     *                listen queue.  If this value is less than zero, the listen
     *                queue size is set to zero.
     */
    public static native int listen(long sock, int backlog);

    /**
     * Accept a new connection request
     * @param sock The socket we are listening on.
     * @param pool The pool for the new socket.
     * @return  A copy of the socket that is connected to the socket that
     *          made the connection request.  This is the socket which should
     *          be used for all future communication.
     */
    public static native long acceptx(long sock, long pool)
        throws Exception;

    /**
     * Accept a new connection request
     * @param sock The socket we are listening on.
     * @return  A copy of the socket that is connected to the socket that
     *          made the connection request.  This is the socket which should
     *          be used for all future communication.
     */
    public static native long accept(long sock)
        throws Exception;

    /**
     * Set an OS level accept filter.
     * @param sock The socket to put the accept filter on.
     * @param name The accept filter
     * @param args Any extra args to the accept filter.  Passing NULL here removes
     *             the accept filter.
     */
    public static native int acceptfilter(long sock, String name, String args);

    /**
     * Query the specified socket if at the OOB/Urgent data mark
     * @param sock The socket to query
     * @return True if socket is at the OOB/urgent mark,
     *         otherwise return false.
     */
    public static native boolean atmark(long sock);

    /**
     * Issue a connection request to a socket either on the same machine
     * or a different one.
     * @param sock The socket we wish to use for our side of the connection
     * @param sa The address of the machine we wish to connect to.
     */
    public static native int connect(long sock, long sa);

    /**
     * Send data over a network.
     * <PRE>
     * This functions acts like a blocking write by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     *
     * It is possible for both bytes to be sent and an error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to send the data over.
     * @param buf The buffer which contains the data to be sent.
     * @param offset Offset in the byte buffer.
     * @param len The number of bytes to write; (-1) for full array.
     * @return The number of bytes send.
     *
     */
    public static native int send(long sock, byte[] buf, int offset, int len);

    /**
     * Send data over a network.
     * <PRE>
     * This functions acts like a blocking write by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     *
     * It is possible for both bytes to be sent and an error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to send the data over.
     * @param buf The Byte buffer which contains the data to be sent.
     * @param offset The offset within the buffer array of the first buffer from
     *               which bytes are to be retrieved; must be non-negative
     *               and no larger than buf.length
     * @param len The maximum number of buffers to be accessed; must be non-negative
     *            and no larger than buf.length - offset
     * @return The number of bytes send.
     *
     */
    public static native int sendb(long sock, ByteBuffer buf,
                                   int offset, int len);

    /**
     * Send data over a network without retry
     * <PRE>
     * This functions acts like a blocking write by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     *
     * It is possible for both bytes to be sent and an error to be returned.
     *
     * </PRE>
     * @param sock The socket to send the data over.
     * @param buf The Byte buffer which contains the data to be sent.
     * @param offset The offset within the buffer array of the first buffer from
     *               which bytes are to be retrieved; must be non-negative
     *               and no larger than buf.length
     * @param len The maximum number of buffers to be accessed; must be non-negative
     *            and no larger than buf.length - offset
     * @return The number of bytes send.
     *
     */
    public static native int sendib(long sock, ByteBuffer buf,
                                    int offset, int len);

    /**
     * Send data over a network using internally set ByteBuffer
     */
    public static native int sendbb(long sock,
                                   int offset, int len);

    /**
     * Send data over a network using internally set ByteBuffer
     * without internal retry.
     */
    public static native int sendibb(long sock,
                                     int offset, int len);

    /**
     * Send multiple packets of data over a network.
     * <PRE>
     * This functions acts like a blocking write by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     * The number of bytes actually sent is stored in argument 3.
     *
     * It is possible for both bytes to be sent and an error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to send the data over.
     * @param vec The array from which to get the data to send.
     *
     */
    public static native int sendv(long sock, byte[][] vec);

    /**
     * @param sock The socket to send from
     * @param where The apr_sockaddr_t describing where to send the data
     * @param flags The flags to use
     * @param buf  The data to send
     * @param offset Offset in the byte buffer.
     * @param len  The length of the data to send
     */
    public static native int sendto(long sock, long where, int flags,
                                    byte[] buf, int offset, int len);

    /**
     * Read data from a network.
     *
     * <PRE>
     * This functions acts like a blocking read by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     * The number of bytes actually received is stored in argument 3.
     *
     * It is possible for both bytes to be received and an APR_EOF or
     * other error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to read the data from.
     * @param buf The buffer to store the data in.
     * @param offset Offset in the byte buffer.
     * @param nbytes The number of bytes to read (-1) for full array.
     * @return the number of bytes received.
     */
    public static native int recv(long sock, byte[] buf, int offset, int nbytes);

    /**
     * Read data from a network with timeout.
     *
     * <PRE>
     * This functions acts like a blocking read by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     * The number of bytes actually received is stored in argument 3.
     *
     * It is possible for both bytes to be received and an APR_EOF or
     * other error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to read the data from.
     * @param buf The buffer to store the data in.
     * @param offset Offset in the byte buffer.
     * @param nbytes The number of bytes to read (-1) for full array.
     * @param timeout The socket timeout in microseconds.
     * @return the number of bytes received.
     */
    public static native int recvt(long sock, byte[] buf, int offset,
                                   int nbytes, long timeout);

    /**
     * Read data from a network.
     *
     * <PRE>
     * This functions acts like a blocking read by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     * The number of bytes actually received is stored in argument 3.
     *
     * It is possible for both bytes to be received and an APR_EOF or
     * other error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to read the data from.
     * @param buf The buffer to store the data in.
     * @param offset Offset in the byte buffer.
     * @param nbytes The number of bytes to read (-1) for full array.
     * @return If &ge; 0, the return value is the number of bytes read. Note a
     *         non-blocking read with no data current available will return
     *         {@link Status#EAGAIN} and EOF will return {@link Status#APR_EOF}.
     */
    public static native int recvb(long sock, ByteBuffer buf,
                                   int offset, int nbytes);
    /**
     * Read data from a network using internally set ByteBuffer.
     *
     * @return If &gt; 0, the return value is the number of bytes read. If == 0,
     *         the return value indicates EOF and if &lt; 0 the return value is the
     *         error code. Note a non-blocking read with no data current
     *         available will return {@link Status#EAGAIN} not zero.
     */
    public static native int recvbb(long sock,
                                    int offset, int nbytes);
    /**
     * Read data from a network with timeout.
     *
     * <PRE>
     * This functions acts like a blocking read by default.  To change
     * this behavior, use apr_socket_timeout_set() or the APR_SO_NONBLOCK
     * socket option.
     * The number of bytes actually received is stored in argument 3.
     *
     * It is possible for both bytes to be received and an APR_EOF or
     * other error to be returned.
     *
     * APR_EINTR is never returned.
     * </PRE>
     * @param sock The socket to read the data from.
     * @param buf The buffer to store the data in.
     * @param offset Offset in the byte buffer.
     * @param nbytes The number of bytes to read (-1) for full array.
     * @param timeout The socket timeout in microseconds.
     * @return the number of bytes received.
     */
    public static native int recvbt(long sock, ByteBuffer buf,
                                    int offset, int nbytes, long timeout);
    /**
     * Read data from a network with timeout using internally set ByteBuffer
     */
    public static native int recvbbt(long sock,
                                     int offset, int nbytes, long timeout);

    /**
     * @param from The apr_sockaddr_t to fill in the recipient info
     * @param sock The socket to use
     * @param flags The flags to use
     * @param buf  The buffer to use
     * @param offset Offset in the byte buffer.
     * @param nbytes The number of bytes to read (-1) for full array.
     * @return the number of bytes received.
     */
    public static native int recvfrom(long from, long sock, int flags,
                                      byte[] buf, int offset, int nbytes);

    /**
     * Setup socket options for the specified socket
     * @param sock The socket to set up.
     * @param opt The option we would like to configure.  One of:
     * <PRE>
     * APR_SO_DEBUG      --  turn on debugging information
     * APR_SO_KEEPALIVE  --  keep connections active
     * APR_SO_LINGER     --  lingers on close if data is present
     * APR_SO_NONBLOCK   --  Turns blocking on/off for socket
     *                       When this option is enabled, use
     *                       the APR_STATUS_IS_EAGAIN() macro to
     *                       see if a send or receive function
     *                       could not transfer data without
     *                       blocking.
     * APR_SO_REUSEADDR  --  The rules used in validating addresses
     *                       supplied to bind should allow reuse
     *                       of local addresses.
     * APR_SO_SNDBUF     --  Set the SendBufferSize
     * APR_SO_RCVBUF     --  Set the ReceiveBufferSize
     * </PRE>
     * @param on Value for the option.
     */
    public static native int optSet(long sock, int opt, int on);

    /**
     * Query socket options for the specified socket
     * @param sock The socket to query
     * @param opt The option we would like to query.  One of:
     * <PRE>
     * APR_SO_DEBUG      --  turn on debugging information
     * APR_SO_KEEPALIVE  --  keep connections active
     * APR_SO_LINGER     --  lingers on close if data is present
     * APR_SO_NONBLOCK   --  Turns blocking on/off for socket
     * APR_SO_REUSEADDR  --  The rules used in validating addresses
     *                       supplied to bind should allow reuse
     *                       of local addresses.
     * APR_SO_SNDBUF     --  Set the SendBufferSize
     * APR_SO_RCVBUF     --  Set the ReceiveBufferSize
     * APR_SO_DISCONNECTED -- Query the disconnected state of the socket.
     *                       (Currently only used on Windows)
     * </PRE>
     * @return Socket option returned on the call.
     */
    public static native int optGet(long sock, int opt)
        throws Exception;

    /**
     * Setup socket timeout for the specified socket
     * @param sock The socket to set up.
     * @param t Value for the timeout in microseconds.
     * <PRE>
     * t &gt; 0  -- read and write calls return APR_TIMEUP if specified time
     *           elapses with no data read or written
     * t == 0 -- read and write calls never block
     * t &lt; 0  -- read and write calls block
     * </PRE>
     */
    public static native int timeoutSet(long sock, long t);

    /**
     * Query socket timeout for the specified socket
     * @param sock The socket to query
     * @return Socket timeout returned from the query.
     */
    public static native long timeoutGet(long sock)
        throws Exception;

    /**
     * Send a file from an open file descriptor to a socket, along with
     * optional headers and trailers.
     * <br>
     * This functions acts like a blocking write by default.  To change
     *         this behavior, use apr_socket_timeout_set() or the
     *         APR_SO_NONBLOCK socket option.
     * The number of bytes actually sent is stored in the len parameter.
     * The offset parameter is passed by reference for no reason; its
     * value will never be modified by the apr_socket_sendfile() function.
     * @param sock The socket to which we're writing
     * @param file The open file from which to read
     * @param headers Array containing the headers to send
     * @param trailers Array containing the trailers to send
     * @param offset Offset into the file where we should begin writing
     * @param len Number of bytes to send from the file
     * @param flags APR flags that are mapped to OS specific flags
     * @return Number of bytes actually sent, including headers,
     *         file, and trailers
     *
     */
    public static native long sendfile(long sock, long file, byte [][] headers,
                                       byte[][] trailers, long offset,
                                       long len, int flags);

    /**
     * Send a file without header and trailer arrays.
     */
    public static native long sendfilen(long sock, long file, long offset,
                                        long len, int flags);

    /**
     * Create a child pool from associated socket pool.
     * @param thesocket The socket to use
     */
    public static native long pool(long thesocket)
        throws Exception;

    /**
     * Private method for getting the socket struct members
     * @param socket The socket to use
     * @param what Struct member to obtain
     * <PRE>
     * SOCKET_GET_POOL  - The socket pool
     * SOCKET_GET_IMPL  - The socket implementation object
     * SOCKET_GET_APRS  - APR socket
     * SOCKET_GET_TYPE  - Socket type
     * </PRE>
     * @return The structure member address
     */
    private static native long get(long socket, int what);

    /**
     * Set internal send ByteBuffer.
     * This function will preset internal Java ByteBuffer for
     * consecutive sendbb calls.
     * @param sock The socket to use
     * @param buf The ByteBuffer
     */
    public static native void setsbb(long sock, ByteBuffer buf);

    /**
     * Set internal receive ByteBuffer.
     * This function will preset internal Java ByteBuffer for
     * consecutive revcvbb/recvbbt calls.
     * @param sock The socket to use
     * @param buf The ByteBuffer
     */
    public static native void setrbb(long sock, ByteBuffer buf);

    /**
     * Set the data associated with the current socket.
     * @param sock The currently open socket.
     * @param data The user data to associate with the socket.
     * @param key The key to associate with the data.
     */
      public static native int dataSet(long sock, String key, Object data);

    /**
     * Return the data associated with the current socket
     * @param key The key to associate with the user data.
     * @param sock The currently open socket.
     * @return Data or null in case of error.
     */
     public static native Object dataGet(long sock, String key);
}
