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
package org.apache.tomcat.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.websocket.Extension;
import javax.websocket.Extension.Parameter;
import javax.websocket.SendHandler;

import org.apache.tomcat.util.res.StringManager;

public class PerMessageDeflate implements Transformation {

    private static final StringManager sm = StringManager.getManager(Constants.PACKAGE_NAME);

    private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
    private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
    private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
    private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";

    private static final int RSV_BITMASK = 0x4;
    private static final byte[] EOM_BYTES = new byte[] {0, 0, -1, -1};

    public static final String NAME = "permessage-deflate";

    private final boolean serverContextTakeover;
    private final int serverMaxWindowBits;
    private final boolean clientContextTakeover;
    private final int clientMaxWindowBits;
    private final boolean isServer;
    private final Inflater inflater = new Inflater(true);
    private final ByteBuffer readBuffer = ByteBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE);
    private final Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
    private final byte[] EOM_BUFFER = new byte[EOM_BYTES.length + 1];

    private volatile Transformation next;
    private volatile boolean skipDecompression = false;
    private volatile ByteBuffer writeBuffer = ByteBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE);
    private volatile boolean firstCompressedFrameWritten = false;

    static PerMessageDeflate negotiate(List<List<Parameter>> preferences, boolean isServer) {
        // Accept the first preference that the endpoint is able to support
        for (List<Parameter> preference : preferences) {
            boolean ok = true;
            boolean serverContextTakeover = true;
            int serverMaxWindowBits = -1;
            boolean clientContextTakeover = true;
            int clientMaxWindowBits = -1;

            for (Parameter param : preference) {
                if (SERVER_NO_CONTEXT_TAKEOVER.equals(param.getName())) {
                    if (serverContextTakeover) {
                        serverContextTakeover = false;
                    } else {
                        // Duplicate definition
                        throw new IllegalArgumentException(sm.getString(
                                "perMessageDeflate.duplicateParameter",
                                SERVER_NO_CONTEXT_TAKEOVER ));
                    }
                } else if (CLIENT_NO_CONTEXT_TAKEOVER.equals(param.getName())) {
                    if (clientContextTakeover) {
                        clientContextTakeover = false;
                    } else {
                        // Duplicate definition
                        throw new IllegalArgumentException(sm.getString(
                                "perMessageDeflate.duplicateParameter",
                                CLIENT_NO_CONTEXT_TAKEOVER ));
                    }
                } else if (SERVER_MAX_WINDOW_BITS.equals(param.getName())) {
                    if (serverMaxWindowBits == -1) {
                        serverMaxWindowBits = Integer.parseInt(param.getValue());
                        if (serverMaxWindowBits < 8 || serverMaxWindowBits > 15) {
                            throw new IllegalArgumentException(sm.getString(
                                    "perMessageDeflate.invalidWindowSize",
                                    SERVER_MAX_WINDOW_BITS,
                                    Integer.valueOf(serverMaxWindowBits)));
                        }
                        // Java SE API (as of Java 8) does not expose the API to
                        // control the Window size. It is effectively hard-coded
                        // to 15
                        if (isServer && serverMaxWindowBits != 15) {
                            ok = false;
                            break;
                            // Note server window size is not an issue for the
                            // client since the client will assume 15 and if the
                            // server uses a smaller window everything will
                            // still work
                        }
                    } else {
                        // Duplicate definition
                        throw new IllegalArgumentException(sm.getString(
                                "perMessageDeflate.duplicateParameter",
                                SERVER_MAX_WINDOW_BITS ));
                    }
                } else if (CLIENT_MAX_WINDOW_BITS.equals(param.getName())) {
                    if (clientMaxWindowBits == -1) {
                        if (param.getValue() == null) {
                            // Hint to server that the client supports this
                            // option. Java SE API (as of Java 8) does not
                            // expose the API to control the Window size. It is
                            // effectively hard-coded to 15
                            clientMaxWindowBits = 15;
                        } else {
                            clientMaxWindowBits = Integer.parseInt(param.getValue());
                            if (clientMaxWindowBits < 8 || clientMaxWindowBits > 15) {
                                throw new IllegalArgumentException(sm.getString(
                                        "perMessageDeflate.invalidWindowSize",
                                        CLIENT_MAX_WINDOW_BITS,
                                        Integer.valueOf(clientMaxWindowBits)));
                            }
                        }
                        // Java SE API (as of Java 8) does not expose the API to
                        // control the Window size. It is effectively hard-coded
                        // to 15
                        if (!isServer && clientMaxWindowBits != 15) {
                            ok = false;
                            break;
                            // Note client window size is not an issue for the
                            // server since the server will assume 15 and if the
                            // client uses a smaller window everything will
                            // still work
                        }
                    } else {
                        // Duplicate definition
                        throw new IllegalArgumentException(sm.getString(
                                "perMessageDeflate.duplicateParameter",
                                CLIENT_MAX_WINDOW_BITS ));
                    }
                } else {
                    // Unknown parameter
                    throw new IllegalArgumentException(sm.getString(
                            "perMessageDeflate.unknownParameter", param.getName()));
                }
            }
            if (ok) {
                return new PerMessageDeflate(serverContextTakeover, serverMaxWindowBits,
                        clientContextTakeover, clientMaxWindowBits, isServer);
            }
        }
        // Failed to negotiate agreeable terms
        return null;
    }


    private PerMessageDeflate(boolean serverContextTakeover, int serverMaxWindowBits,
            boolean clientContextTakeover, int clientMaxWindowBits, boolean isServer) {
        this.serverContextTakeover = serverContextTakeover;
        this.serverMaxWindowBits = serverMaxWindowBits;
        this.clientContextTakeover = clientContextTakeover;
        this.clientMaxWindowBits = clientMaxWindowBits;
        this.isServer = isServer;
    }


    @Override
    public TransformationResult getMoreData(byte opCode, boolean fin, int rsv, ByteBuffer dest)
            throws IOException {
        // Control frames are never compressed and may appear in the middle of
        // a WebSocket method. Pass them straight through.
        if (Util.isControl(opCode)) {
            return next.getMoreData(opCode, fin, rsv, dest);
        }

        if (!Util.isContinuation(opCode)) {
            // First frame in new message
            skipDecompression = (rsv & RSV_BITMASK) == 0;
        }

        // Pass uncompressed frames straight through.
        if (skipDecompression) {
            return next.getMoreData(opCode, fin, rsv, dest);
        }

        int written;
        boolean usedEomBytes = false;

        while (dest.remaining() > 0) {
            // Space available in destination. Try and fill it.
            try {
                written = inflater.inflate(
                        dest.array(), dest.arrayOffset() + dest.position(), dest.remaining());
            } catch (DataFormatException e) {
                throw new IOException(sm.getString("perMessageDeflate.deflateFailed"), e);
            }
            dest.position(dest.position() + written);

            if (inflater.needsInput() && !usedEomBytes ) {
                if (dest.hasRemaining()) {
                    readBuffer.clear();
                    TransformationResult nextResult =
                            next.getMoreData(opCode, fin, (rsv ^ RSV_BITMASK), readBuffer);
                    inflater.setInput(
                            readBuffer.array(), readBuffer.arrayOffset(), readBuffer.position());
                    if (TransformationResult.UNDERFLOW.equals(nextResult)) {
                        return nextResult;
                    } else if (TransformationResult.END_OF_FRAME.equals(nextResult) &&
                            readBuffer.position() == 0) {
                        if (fin) {
                            inflater.setInput(EOM_BYTES);
                            usedEomBytes = true;
                        } else {
                            return TransformationResult.END_OF_FRAME;
                        }
                    }
                }
            } else if (written == 0) {
                if (fin && (isServer && !clientContextTakeover ||
                        !isServer && !serverContextTakeover)) {
                    inflater.reset();
                }
                return TransformationResult.END_OF_FRAME;
            }
        }

        return TransformationResult.OVERFLOW;
    }


    @Override
    public boolean validateRsv(int rsv, byte opCode) {
        if (Util.isControl(opCode)) {
            if ((rsv & RSV_BITMASK) != 0) {
                return false;
            } else {
                if (next == null) {
                    return true;
                } else {
                    return next.validateRsv(rsv, opCode);
                }
            }
        } else {
            int rsvNext = rsv;
            if ((rsv & RSV_BITMASK) != 0) {
                rsvNext = rsv ^ RSV_BITMASK;
            }
            if (next == null) {
                return true;
            } else {
                return next.validateRsv(rsvNext, opCode);
            }
        }
    }


    @Override
    public Extension getExtensionResponse() {
        Extension result = new WsExtension(NAME);

        List<Extension.Parameter> params = result.getParameters();

        if (!serverContextTakeover) {
            params.add(new WsExtensionParameter(SERVER_NO_CONTEXT_TAKEOVER, null));
        }
        if (serverMaxWindowBits != -1) {
            params.add(new WsExtensionParameter(SERVER_MAX_WINDOW_BITS,
                    Integer.toString(serverMaxWindowBits)));
        }
        if (!clientContextTakeover) {
            params.add(new WsExtensionParameter(CLIENT_NO_CONTEXT_TAKEOVER, null));
        }
        if (clientMaxWindowBits != -1) {
            params.add(new WsExtensionParameter(CLIENT_MAX_WINDOW_BITS,
                    Integer.toString(clientMaxWindowBits)));
        }

        return result;
    }


    @Override
    public void setNext(Transformation t) {
        if (next == null) {
            this.next = t;
        } else {
            next.setNext(t);
        }
    }


    @Override
    public boolean validateRsvBits(int i) {
        if ((i & RSV_BITMASK) != 0) {
            return false;
        }
        if (next == null) {
            return true;
        } else {
            return next.validateRsvBits(i | RSV_BITMASK);
        }
    }


    @Override
    public List<MessagePart> sendMessagePart(List<MessagePart> uncompressedParts) {
        List<MessagePart> allCompressedParts = new ArrayList<MessagePart>();

        // Flag to track if a message is completely empty
        boolean emptyMessage = true;

        for (MessagePart uncompressedPart : uncompressedParts) {
            byte opCode = uncompressedPart.getOpCode();
            boolean emptyPart = uncompressedPart.getPayload().limit() == 0;
            emptyMessage = emptyMessage && emptyPart;
            if (Util.isControl(opCode)) {
                // Control messages can appear in the middle of other messages
                // and must not be compressed. Pass it straight through
                allCompressedParts.add(uncompressedPart);
            } else if (emptyMessage && uncompressedPart.isFin()) {
                // Zero length messages can't be compressed so pass the
                // final (empty) part straight through.
                allCompressedParts.add(uncompressedPart);
            } else {
                List<MessagePart> compressedParts = new ArrayList<MessagePart>();
                ByteBuffer uncompressedPayload = uncompressedPart.getPayload();
                SendHandler uncompressedIntermediateHandler =
                        uncompressedPart.getIntermediateHandler();

                deflater.setInput(uncompressedPayload.array(),
                        uncompressedPayload.arrayOffset() + uncompressedPayload.position(),
                        uncompressedPayload.remaining());

                int flush = (uncompressedPart.isFin() ? Deflater.SYNC_FLUSH : Deflater.NO_FLUSH);
                boolean deflateRequired = true;

                while(deflateRequired) {
                    ByteBuffer compressedPayload = writeBuffer;

                    int written = deflater.deflate(compressedPayload.array(),
                            compressedPayload.arrayOffset() + compressedPayload.position(),
                            compressedPayload.remaining(), flush);
                    compressedPayload.position(compressedPayload.position() + written);

                    if (!uncompressedPart.isFin() && compressedPayload.hasRemaining() && deflater.needsInput()) {
                        // This message part has been fully processed by the
                        // deflater. Fire the send handler for this message part
                        // and move on to the next message part.
                        break;
                    }

                    // If this point is reached, a new compressed message part
                    // will be created...
                    MessagePart compressedPart;

                    // .. and a new writeBuffer will be required.
                    writeBuffer = ByteBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE);

                    // Flip the compressed payload ready for writing
                    compressedPayload.flip();

                    boolean fin = uncompressedPart.isFin();
                    boolean full = compressedPayload.limit() == compressedPayload.capacity();
                    boolean needsInput = deflater.needsInput();

                    if (fin && !full && needsInput) {
                        // End of compressed message. Drop EOM bytes and output.
                        compressedPayload.limit(compressedPayload.limit() - EOM_BYTES.length);
                        compressedPart = new MessagePart(true, getRsv(uncompressedPart),
                                opCode, compressedPayload, uncompressedIntermediateHandler,
                                uncompressedIntermediateHandler);
                        deflateRequired = false;
                        startNewMessage();
                    } else if (full && !needsInput) {
                        // Write buffer full and input message not fully read.
                        // Output and start new compressed part.
                        compressedPart = new MessagePart(false, getRsv(uncompressedPart),
                                opCode, compressedPayload, uncompressedIntermediateHandler,
                                uncompressedIntermediateHandler);
                    } else if (!fin && full && needsInput) {
                        // Write buffer full and input message not fully read.
                        // Output and get more data.
                        compressedPart = new MessagePart(false, getRsv(uncompressedPart),
                                opCode, compressedPayload, uncompressedIntermediateHandler,
                                uncompressedIntermediateHandler);
                        deflateRequired = false;
                    } else if (fin && full && needsInput) {
                        // Write buffer full. Input fully read. Deflater may be
                        // in one of four states:
                        // - output complete (just happened to align with end of
                        //   buffer
                        // - in middle of EOM bytes
                        // - about to write EOM bytes
                        // - more data to write
                        int eomBufferWritten = deflater.deflate(EOM_BUFFER, 0, EOM_BUFFER.length, Deflater.SYNC_FLUSH);
                        if (eomBufferWritten < EOM_BUFFER.length) {
                            // EOM has just been completed
                            compressedPayload.limit(compressedPayload.limit() - EOM_BYTES.length + eomBufferWritten);
                            compressedPart = new MessagePart(true,
                                    getRsv(uncompressedPart), opCode, compressedPayload,
                                    uncompressedIntermediateHandler, uncompressedIntermediateHandler);
                            deflateRequired = false;
                            startNewMessage();
                        } else {
                            // More data to write
                            // Copy bytes to new write buffer
                            writeBuffer.put(EOM_BUFFER, 0, eomBufferWritten);
                            compressedPart = new MessagePart(false,
                                    getRsv(uncompressedPart), opCode, compressedPayload,
                                    uncompressedIntermediateHandler, uncompressedIntermediateHandler);
                        }
                    } else {
                        throw new IllegalStateException("Should never happen");
                    }

                    // Add the newly created compressed part to the set of parts
                    // to pass on to the next transformation.
                    compressedParts.add(compressedPart);
                }

                SendHandler uncompressedEndHandler = uncompressedPart.getEndHandler();
                int size = compressedParts.size();
                if (size > 0) {
                    compressedParts.get(size - 1).setEndHandler(uncompressedEndHandler);
                }

                allCompressedParts.addAll(compressedParts);
            }
        }

        if (next == null) {
            return allCompressedParts;
        } else {
            return next.sendMessagePart(allCompressedParts);
        }
    }


    private void startNewMessage() {
        firstCompressedFrameWritten = false;
        if (isServer && !serverContextTakeover || !isServer && !clientContextTakeover) {
            deflater.reset();
        }
    }


    private int getRsv(MessagePart uncompressedMessagePart) {
        int result = uncompressedMessagePart.getRsv();
        if (!firstCompressedFrameWritten) {
            result += RSV_BITMASK;
            firstCompressedFrameWritten = true;
        }
        return result;
    }


    @Override
    public void close() {
        // There will always be a next transformation
        next.close();
        inflater.end();
        deflater.end();
    }
}
