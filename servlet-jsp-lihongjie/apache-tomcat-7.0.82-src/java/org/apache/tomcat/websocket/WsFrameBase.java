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
package org.apache.tomcat.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.PongMessage;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.res.StringManager;

/**
 * Takes the ServletInputStream, processes the WebSocket frames it contains and
 * extracts the messages. WebSocket Pings received will be responded to
 * automatically without any action required by the application.
 */
public abstract class WsFrameBase {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    // Connection level attributes
    protected final WsSession wsSession;
    protected final byte[] inputBuffer;
    private final Transformation transformation;

    // Attributes for control messages
    // Control messages can appear in the middle of other messages so need
    // separate attributes
    private final ByteBuffer controlBufferBinary = ByteBuffer.allocate(125);
    private final CharBuffer controlBufferText = CharBuffer.allocate(125);

    // Attributes of the current message
    private final CharsetDecoder utf8DecoderControl = new Utf8Decoder().
            onMalformedInput(CodingErrorAction.REPORT).
            onUnmappableCharacter(CodingErrorAction.REPORT);
    private final CharsetDecoder utf8DecoderMessage = new Utf8Decoder().
            onMalformedInput(CodingErrorAction.REPORT).
            onUnmappableCharacter(CodingErrorAction.REPORT);
    private boolean continuationExpected = false;
    private boolean textMessage = false;
    private ByteBuffer messageBufferBinary;
    private CharBuffer messageBufferText;
    // Cache the message handler in force when the message starts so it is used
    // consistently for the entire message
    private MessageHandler binaryMsgHandler = null;
    private MessageHandler textMsgHandler = null;

    // Attributes of the current frame
    private boolean fin = false;
    private int rsv = 0;
    private byte opCode = 0;
    private final byte[] mask = new byte[4];
    private int maskIndex = 0;
    private long payloadLength = 0;
    private long payloadWritten = 0;

    // Attributes tracking state
    private State state = State.NEW_FRAME;
    private volatile boolean open = true;
    private int readPos = 0;
    protected int writePos = 0;

    public WsFrameBase(WsSession wsSession, Transformation transformation) {
        inputBuffer = new byte[Constants.DEFAULT_BUFFER_SIZE];
        messageBufferBinary =
                ByteBuffer.allocate(wsSession.getMaxBinaryMessageBufferSize());
        messageBufferText =
                CharBuffer.allocate(wsSession.getMaxTextMessageBufferSize());
        this.wsSession = wsSession;
        Transformation finalTransformation;
        if (isMasked()) {
            finalTransformation = new UnmaskTransformation();
        } else {
            finalTransformation = new NoopTransformation();
        }
        if (transformation == null) {
            this.transformation = finalTransformation;
        } else {
            transformation.setNext(finalTransformation);
            this.transformation = transformation;
        }
    }


    protected void processInputBuffer() throws IOException {
        while (true) {
            wsSession.updateLastActive();
            if (state == State.NEW_FRAME) {
                if (!processInitialHeader()) {
                    break;
                }
                // If a close frame has been received, no further data should
                // have seen
                if (!open) {
                    throw new IOException(sm.getString("wsFrame.closed"));
                }
            }
            if (state == State.PARTIAL_HEADER) {
                if (!processRemainingHeader()) {
                    break;
                }
            }
            if (state == State.DATA) {
                if (!processData()) {
                    break;
                }
            }
        }
    }


    /**
     * @return <code>true</code> if sufficient data was present to process all
     *         of the initial header
     */
    private boolean processInitialHeader() throws IOException {
        // Need at least two bytes of data to do this
        if (writePos - readPos < 2) {
            return false;
        }
        int b = inputBuffer[readPos++];
        fin = (b & 0x80) != 0;
        rsv = (b & 0x70) >>> 4;
        opCode = (byte) (b & 0x0F);
        if (!transformation.validateRsv(rsv, opCode)) {
            throw new WsIOException(new CloseReason(
                    CloseCodes.PROTOCOL_ERROR,
                    sm.getString("wsFrame.wrongRsv", Integer.valueOf(rsv),
                            Integer.valueOf(opCode))));
        }

        if (Util.isControl(opCode)) {
            if (!fin) {
                throw new WsIOException(new CloseReason(
                        CloseCodes.PROTOCOL_ERROR,
                        sm.getString("wsFrame.controlFragmented")));
            }
            if (opCode != Constants.OPCODE_PING &&
                    opCode != Constants.OPCODE_PONG &&
                    opCode != Constants.OPCODE_CLOSE) {
                throw new WsIOException(new CloseReason(
                        CloseCodes.PROTOCOL_ERROR,
                        sm.getString("wsFrame.invalidOpCode",
                                Integer.valueOf(opCode))));
            }
        } else {
            if (continuationExpected) {
                if (!Util.isContinuation(opCode)) {
                    throw new WsIOException(new CloseReason(
                            CloseCodes.PROTOCOL_ERROR,
                            sm.getString("wsFrame.noContinuation")));
                }
            } else {
                try {
                    if (opCode == Constants.OPCODE_BINARY) {
                        // New binary message
                        textMessage = false;
                        int size = wsSession.getMaxBinaryMessageBufferSize();
                        if (size != messageBufferBinary.capacity()) {
                            messageBufferBinary = ByteBuffer.allocate(size);
                        }
                        binaryMsgHandler = wsSession.getBinaryMessageHandler();
                        textMsgHandler = null;
                    } else if (opCode == Constants.OPCODE_TEXT) {
                        // New text message
                        textMessage = true;
                        int size = wsSession.getMaxTextMessageBufferSize();
                        if (size != messageBufferText.capacity()) {
                            messageBufferText = CharBuffer.allocate(size);
                        }
                        binaryMsgHandler = null;
                        textMsgHandler = wsSession.getTextMessageHandler();
                    } else {
                        throw new WsIOException(new CloseReason(
                                CloseCodes.PROTOCOL_ERROR,
                                sm.getString("wsFrame.invalidOpCode",
                                        Integer.valueOf(opCode))));
                    }
                } catch (IllegalStateException ise) {
                    // Thrown if the session is already closed
                    throw new WsIOException(new CloseReason(
                            CloseCodes.PROTOCOL_ERROR,
                            sm.getString("wsFrame.sessionClosed")));
                }
            }
            continuationExpected = !fin;
        }
        b = inputBuffer[readPos++];
        // Client data must be masked
        if ((b & 0x80) == 0 && isMasked()) {
            throw new WsIOException(new CloseReason(
                    CloseCodes.PROTOCOL_ERROR,
                    sm.getString("wsFrame.notMasked")));
        }
        payloadLength = b & 0x7F;
        state = State.PARTIAL_HEADER;
        if (getLog().isDebugEnabled()) {
            getLog().debug(sm.getString("wsFrame.partialHeaderComplete", Boolean.toString(fin),
                    Integer.toString(rsv), Integer.toString(opCode), Long.toString(payloadLength)));
        }
        return true;
    }


    protected abstract boolean isMasked();
    protected abstract Log getLog();


    /**
     * @return <code>true</code> if sufficient data was present to complete the
     *         processing of the header
     */
    private boolean processRemainingHeader() throws IOException {
        // Ignore the 2 bytes already read. 4 for the mask
        int headerLength;
        if (isMasked()) {
            headerLength = 4;
        } else {
            headerLength = 0;
        }
        // Add additional bytes depending on length
        if (payloadLength == 126) {
            headerLength += 2;
        } else if (payloadLength == 127) {
            headerLength += 8;
        }
        if (writePos - readPos < headerLength) {
            return false;
        }
        // Calculate new payload length if necessary
        if (payloadLength == 126) {
            payloadLength = byteArrayToLong(inputBuffer, readPos, 2);
            readPos += 2;
        } else if (payloadLength == 127) {
            payloadLength = byteArrayToLong(inputBuffer, readPos, 8);
            readPos += 8;
        }
        if (Util.isControl(opCode)) {
            if (payloadLength > 125) {
                throw new WsIOException(new CloseReason(
                        CloseCodes.PROTOCOL_ERROR,
                        sm.getString("wsFrame.controlPayloadTooBig",
                                Long.valueOf(payloadLength))));
            }
            if (!fin) {
                throw new WsIOException(new CloseReason(
                        CloseCodes.PROTOCOL_ERROR,
                        sm.getString("wsFrame.controlNoFin")));
            }
        }
        if (isMasked()) {
            System.arraycopy(inputBuffer, readPos, mask, 0, 4);
            readPos += 4;
        }
        state = State.DATA;
        return true;
    }


    private boolean processData() throws IOException {
        boolean result;
        if (Util.isControl(opCode)) {
            result = processDataControl();
        } else if (textMessage) {
            if (textMsgHandler == null) {
                result = swallowInput();
            } else {
                result = processDataText();
            }
        } else {
            if (binaryMsgHandler == null) {
                result = swallowInput();
            } else {
                result = processDataBinary();
            }
        }
        checkRoomPayload();
        return result;
    }


    private boolean processDataControl() throws IOException {
        TransformationResult tr = transformation.getMoreData(opCode, fin, rsv, controlBufferBinary);
        if (TransformationResult.UNDERFLOW.equals(tr)) {
            return false;
        }
        // Control messages have fixed message size so
        // TransformationResult.OVERFLOW is not possible here

        controlBufferBinary.flip();
        if (opCode == Constants.OPCODE_CLOSE) {
            open = false;
            String reason = null;
            int code = CloseCodes.NORMAL_CLOSURE.getCode();
            if (controlBufferBinary.remaining() == 1) {
                controlBufferBinary.clear();
                // Payload must be zero or greater than 2
                throw new WsIOException(new CloseReason(
                        CloseCodes.PROTOCOL_ERROR,
                        sm.getString("wsFrame.oneByteCloseCode")));
            }
            if (controlBufferBinary.remaining() > 1) {
                code = controlBufferBinary.getShort();
                if (controlBufferBinary.remaining() > 0) {
                    CoderResult cr = utf8DecoderControl.decode(
                            controlBufferBinary, controlBufferText, true);
                    if (cr.isError()) {
                        controlBufferBinary.clear();
                        controlBufferText.clear();
                        throw new WsIOException(new CloseReason(
                                CloseCodes.PROTOCOL_ERROR,
                                sm.getString("wsFrame.invalidUtf8Close")));
                    }
                    // There will be no overflow as the output buffer is big
                    // enough. There will be no underflow as all the data is
                    // passed to the decoder in a single call.
                    controlBufferText.flip();
                    reason = controlBufferText.toString();
                }
            }
            wsSession.onClose(new CloseReason(Util.getCloseCode(code), reason));
        } else if (opCode == Constants.OPCODE_PING) {
            if (wsSession.isOpen()) {
                wsSession.getBasicRemote().sendPong(controlBufferBinary);
            }
        } else if (opCode == Constants.OPCODE_PONG) {
            MessageHandler.Whole<PongMessage> mhPong =
                    wsSession.getPongMessageHandler();
            if (mhPong != null) {
                try {
                    mhPong.onMessage(new WsPongMessage(controlBufferBinary));
                } catch (Throwable t) {
                    handleThrowableOnSend(t);
                } finally {
                    controlBufferBinary.clear();
                }
            }
        } else {
            // Should have caught this earlier but just in case...
            controlBufferBinary.clear();
            throw new WsIOException(new CloseReason(
                    CloseCodes.PROTOCOL_ERROR,
                    sm.getString("wsFrame.invalidOpCode",
                            Integer.valueOf(opCode))));
        }
        controlBufferBinary.clear();
        newFrame();
        return true;
    }


    @SuppressWarnings("unchecked")
    private void sendMessageText(boolean last) throws WsIOException {
        if (textMsgHandler instanceof WrappedMessageHandler) {
            long maxMessageSize =
                    ((WrappedMessageHandler) textMsgHandler).getMaxMessageSize();
            if (maxMessageSize > -1 &&
                    messageBufferText.remaining() > maxMessageSize) {
                throw new WsIOException(new CloseReason(CloseCodes.TOO_BIG,
                        sm.getString("wsFrame.messageTooBig",
                                Long.valueOf(messageBufferText.remaining()),
                                Long.valueOf(maxMessageSize))));
            }
        }

        try {
            if (textMsgHandler instanceof MessageHandler.Partial<?>) {
                ((MessageHandler.Partial<String>) textMsgHandler).onMessage(
                        messageBufferText.toString(), last);
            } else {
                // Caller ensures last == true if this branch is used
                ((MessageHandler.Whole<String>) textMsgHandler).onMessage(
                        messageBufferText.toString());
            }
        } catch (Throwable t) {
            handleThrowableOnSend(t);
        } finally {
            messageBufferText.clear();
        }
    }


    private boolean processDataText() throws IOException {
        // Copy the available data to the buffer
        TransformationResult tr = transformation.getMoreData(opCode, fin, rsv, messageBufferBinary);
        while (!TransformationResult.END_OF_FRAME.equals(tr)) {
            // Frame not complete - we ran out of something
            // Convert bytes to UTF-8
            messageBufferBinary.flip();
            while (true) {
                CoderResult cr = utf8DecoderMessage.decode(
                        messageBufferBinary, messageBufferText, false);
                if (cr.isError()) {
                    throw new WsIOException(new CloseReason(
                            CloseCodes.NOT_CONSISTENT,
                            sm.getString("wsFrame.invalidUtf8")));
                } else if (cr.isOverflow()) {
                    // Ran out of space in text buffer - flush it
                    if (usePartial()) {
                        messageBufferText.flip();
                        sendMessageText(false);
                        messageBufferText.clear();
                    } else {
                        throw new WsIOException(new CloseReason(
                                CloseCodes.TOO_BIG,
                                sm.getString("wsFrame.textMessageTooBig")));
                    }
                } else if (cr.isUnderflow()) {
                    // Compact what we have to create as much space as possible
                    messageBufferBinary.compact();

                    // Need more input
                    // What did we run out of?
                    if (TransformationResult.OVERFLOW.equals(tr)) {
                        // Ran out of message buffer - exit inner loop and
                        // refill
                        break;
                    } else {
                        // TransformationResult.UNDERFLOW
                        // Ran out of input data - get some more
                        return false;
                    }
                }
            }
            // Read more input data
            tr = transformation.getMoreData(opCode, fin, rsv, messageBufferBinary);
        }

        messageBufferBinary.flip();
        boolean last = false;
        // Frame is fully received
        // Convert bytes to UTF-8
        while (true) {
            CoderResult cr = utf8DecoderMessage.decode(messageBufferBinary,
                    messageBufferText, last);
            if (cr.isError()) {
                throw new WsIOException(new CloseReason(
                        CloseCodes.NOT_CONSISTENT,
                        sm.getString("wsFrame.invalidUtf8")));
            } else if (cr.isOverflow()) {
                // Ran out of space in text buffer - flush it
                if (usePartial()) {
                    messageBufferText.flip();
                    sendMessageText(false);
                    messageBufferText.clear();
                } else {
                    throw new WsIOException(new CloseReason(
                            CloseCodes.TOO_BIG,
                            sm.getString("wsFrame.textMessageTooBig")));
                }
            } else if (cr.isUnderflow() && !last) {
                // End of frame and possible message as well.

                if (continuationExpected) {
                    // If partial messages are supported, send what we have
                    // managed to decode
                    if (usePartial()) {
                        messageBufferText.flip();
                        sendMessageText(false);
                        messageBufferText.clear();
                    }
                    messageBufferBinary.compact();
                    newFrame();
                    // Process next frame
                    return true;
                } else {
                    // Make sure coder has flushed all output
                    last = true;
                }
            } else {
                // End of message
                messageBufferText.flip();
                sendMessageText(true);

                newMessage();
                return true;
            }
        }
    }


    private boolean processDataBinary() throws IOException {
        // Copy the available data to the buffer
        TransformationResult tr = transformation.getMoreData(opCode, fin, rsv, messageBufferBinary);
        while (!TransformationResult.END_OF_FRAME.equals(tr)) {
            // Frame not complete - what did we run out of?
            if (TransformationResult.UNDERFLOW.equals(tr)) {
                // Ran out of input data - get some more
                return false;
            }

            // Ran out of message buffer - flush it
            if (!usePartial()) {
                CloseReason cr = new CloseReason(CloseCodes.TOO_BIG,
                        sm.getString("wsFrame.bufferTooSmall",
                                Integer.valueOf(
                                        messageBufferBinary.capacity()),
                                Long.valueOf(payloadLength)));
                throw new WsIOException(cr);
            }
            messageBufferBinary.flip();
            ByteBuffer copy =
                    ByteBuffer.allocate(messageBufferBinary.limit());
            copy.put(messageBufferBinary);
            copy.flip();
            sendMessageBinary(copy, false);
            messageBufferBinary.clear();
            // Read more data
            tr = transformation.getMoreData(opCode, fin, rsv, messageBufferBinary);
        }

        // Frame is fully received
        // Send the message if either:
        // - partial messages are supported
        // - the message is complete
        if (usePartial() || !continuationExpected) {
            messageBufferBinary.flip();
            ByteBuffer copy =
                    ByteBuffer.allocate(messageBufferBinary.limit());
            copy.put(messageBufferBinary);
            copy.flip();
            sendMessageBinary(copy, !continuationExpected);
            messageBufferBinary.clear();
        }

        if (continuationExpected) {
            // More data for this message expected, start a new frame
            newFrame();
        } else {
            // Message is complete, start a new message
            newMessage();
        }

        return true;
    }


    private void handleThrowableOnSend(Throwable t) throws WsIOException {
        ExceptionUtils.handleThrowable(t);
        wsSession.getLocal().onError(wsSession, t);
        CloseReason cr = new CloseReason(CloseCodes.CLOSED_ABNORMALLY,
                sm.getString("wsFrame.ioeTriggeredClose"));
        throw new WsIOException(cr);
    }


    @SuppressWarnings("unchecked")
    private void sendMessageBinary(ByteBuffer msg, boolean last)
            throws WsIOException {
        if (binaryMsgHandler instanceof WrappedMessageHandler) {
            long maxMessageSize =
                    ((WrappedMessageHandler) binaryMsgHandler).getMaxMessageSize();
            if (maxMessageSize > -1 && msg.remaining() > maxMessageSize) {
                throw new WsIOException(new CloseReason(CloseCodes.TOO_BIG,
                        sm.getString("wsFrame.messageTooBig",
                                Long.valueOf(msg.remaining()),
                                Long.valueOf(maxMessageSize))));
            }
        }
        try {
            if (binaryMsgHandler instanceof MessageHandler.Partial<?>) {
                ((MessageHandler.Partial<ByteBuffer>) binaryMsgHandler).onMessage(msg, last);
            } else {
                // Caller ensures last == true if this branch is used
                ((MessageHandler.Whole<ByteBuffer>) binaryMsgHandler).onMessage(msg);
            }
        } catch(Throwable t) {
            handleThrowableOnSend(t);
        }
    }


    private void newMessage() {
        messageBufferBinary.clear();
        messageBufferText.clear();
        utf8DecoderMessage.reset();
        continuationExpected = false;
        newFrame();
    }


    private void newFrame() {
        if (readPos == writePos) {
            readPos = 0;
            writePos = 0;
        }

        maskIndex = 0;
        payloadWritten = 0;
        state = State.NEW_FRAME;

        // These get reset in processInitialHeader()
        // fin, rsv, opCode, payloadLength, mask

        checkRoomHeaders();
    }


    private void checkRoomHeaders() {
        // Is the start of the current frame too near the end of the input
        // buffer?
        if (inputBuffer.length - readPos < 131) {
            // Limit based on a control frame with a full payload
            makeRoom();
        }
    }


    private void checkRoomPayload() {
        if (inputBuffer.length - readPos - payloadLength + payloadWritten < 0) {
            makeRoom();
        }
    }


    private void makeRoom() {
        System.arraycopy(inputBuffer, readPos, inputBuffer, 0,
                writePos - readPos);
        writePos = writePos - readPos;
        readPos = 0;
    }


    private boolean usePartial() {
        if (Util.isControl(opCode)) {
            return false;
        } else if (textMessage) {
            return textMsgHandler instanceof MessageHandler.Partial<?>;
        } else {
            // Must be binary
            return binaryMsgHandler instanceof MessageHandler.Partial<?>;
        }
    }


    private boolean swallowInput() {
        long toSkip = Math.min(payloadLength - payloadWritten, writePos - readPos);
        readPos += toSkip;
        payloadWritten += toSkip;
        if (payloadWritten == payloadLength) {
            if (continuationExpected) {
                newFrame();
            } else {
                newMessage();
            }
            return true;
        } else {
            return false;
        }
    }


    protected static long byteArrayToLong(byte[] b, int start, int len)
            throws IOException {
        if (len > 8) {
            throw new IOException(sm.getString("wsFrame.byteToLongFail",
                    Long.valueOf(len)));
        }
        int shift = 0;
        long result = 0;
        for (int i = start + len - 1; i >= start; i--) {
            result = result + ((b[i] & 0xFF) << shift);
            shift += 8;
        }
        return result;
    }


    protected boolean isOpen() {
        return open;
    }


    protected Transformation getTransformation() {
        return transformation;
    }


    private static enum State {
        NEW_FRAME, PARTIAL_HEADER, DATA
    }


    private abstract class TerminalTransformation implements Transformation {

        @Override
        public boolean validateRsvBits(int i) {
            // Terminal transformations don't use RSV bits and there is no next
            // transformation so always return true.
            return true;
        }

        @Override
        public Extension getExtensionResponse() {
            // Return null since terminal transformations are not extensions
            return null;
        }

        @Override
        public void setNext(Transformation t) {
            // NO-OP since this is the terminal transformation
        }

        /**
         * {@inheritDoc}
         * <p>
         * Anything other than a value of zero for rsv is invalid.
         */
        @Override
        public boolean validateRsv(int rsv, byte opCode) {
            return rsv == 0;
        }

        @Override
        public void close() {
            // NO-OP for the terminal transformations
        }
    }


    /**
     * For use by the client implementation that needs to obtain payload data
     * without the need for unmasking.
     */
    private final class NoopTransformation extends TerminalTransformation {

        @Override
        public TransformationResult getMoreData(byte opCode, boolean fin, int rsv,
                ByteBuffer dest) {
            // opCode is ignored as the transformation is the same for all
            // opCodes
            // rsv is ignored as it known to be zero at this point
            long toWrite = Math.min(
                    payloadLength - payloadWritten, writePos - readPos);
            toWrite = Math.min(toWrite, dest.remaining());

            dest.put(inputBuffer, readPos, (int) toWrite);
            readPos += toWrite;
            payloadWritten += toWrite;

            if (payloadWritten == payloadLength) {
                return TransformationResult.END_OF_FRAME;
            } else if (readPos == writePos) {
                return TransformationResult.UNDERFLOW;
            } else {
                // !dest.hasRemaining()
                return TransformationResult.OVERFLOW;
            }
        }


        @Override
        public List<MessagePart> sendMessagePart(List<MessagePart> messageParts) {
            // TODO Masking should move to this method
            // NO-OP send so simply return the message unchanged.
            return messageParts;
        }
    }


    /**
     * For use by the server implementation that needs to obtain payload data
     * and unmask it before any further processing.
     */
    private final class UnmaskTransformation extends TerminalTransformation {

        @Override
        public TransformationResult getMoreData(byte opCode, boolean fin, int rsv,
                ByteBuffer dest) {
            // opCode is ignored as the transformation is the same for all
            // opCodes
            // rsv is ignored as it known to be zero at this point
            while (payloadWritten < payloadLength && readPos < writePos &&
                    dest.hasRemaining()) {
                byte b = (byte) ((inputBuffer[readPos] ^ mask[maskIndex]) & 0xFF);
                maskIndex++;
                if (maskIndex == 4) {
                    maskIndex = 0;
                }
                readPos++;
                payloadWritten++;
                dest.put(b);
            }
            if (payloadWritten == payloadLength) {
                return TransformationResult.END_OF_FRAME;
            } else if (readPos == writePos) {
                return TransformationResult.UNDERFLOW;
            } else {
                // !dest.hasRemaining()
                return TransformationResult.OVERFLOW;
            }
        }

        @Override
        public List<MessagePart> sendMessagePart(List<MessagePart> messageParts) {
            // NO-OP send so simply return the message unchanged.
            return messageParts;
        }
    }
}
