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
package websocket.drawboard;

import java.io.IOException;
import java.util.LinkedList;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;

import websocket.drawboard.wsmessages.AbstractWebsocketMessage;
import websocket.drawboard.wsmessages.BinaryWebsocketMessage;
import websocket.drawboard.wsmessages.CloseWebsocketMessage;
import websocket.drawboard.wsmessages.StringWebsocketMessage;

/**
 * Represents a client with methods to send messages asynchronously.
 */
public class Client {

    private final Session session;
    private final Async async;

    /**
     * Contains the messages wich are buffered until the previous
     * send operation has finished.
     */
    private final LinkedList<AbstractWebsocketMessage> messagesToSend =
            new LinkedList<AbstractWebsocketMessage>();
    /**
     * If this client is currently sending a messages asynchronously.
     */
    private volatile boolean isSendingMessage = false;
    /**
     * If this client is closing. If <code>true</code>, new messages to
     * send will be ignored.
     */
    private volatile boolean isClosing = false;
    /**
     * The length of all current buffered messages, to avoid iterating
     * over a linked list.
     */
    private volatile long messagesToSendLength = 0;

    public Client(Session session) {
        this.session = session;
        this.async = session.getAsyncRemote();
    }

    /**
     * Asynchronously closes the Websocket session. This will wait until all
     * remaining messages have been sent to the Client and then close
     * the Websocket session.
     */
    public void close() {
        sendMessage(new CloseWebsocketMessage());
    }

    /**
     * Sends the given message asynchronously to the client.
     * If there is already a async sending in progress, then the message
     * will be buffered and sent when possible.<br><br>
     *
     * This method can be called from multiple threads.
     * @param msg
     */
    public void sendMessage(AbstractWebsocketMessage msg) {
        synchronized (messagesToSend) {
            if (!isClosing) {
                // Check if we have a Close message
                if (msg instanceof CloseWebsocketMessage) {
                    isClosing = true;
                }

                if (isSendingMessage) {
                    // Check if the buffered messages exceed
                    // a specific amount - in that case, disconnect the client
                    // to prevent DoS.
                    // In this case we check if there are >= 1000 messages
                    // or length(of all messages) >= 1000000 bytes.
                    if (messagesToSend.size() >= 1000
                            || messagesToSendLength >= 1000000) {
                        isClosing = true;

                        // Discard the new message and close the session immediately.
                        CloseReason cr = new CloseReason(
                                CloseCodes.VIOLATED_POLICY,
                                "Send Buffer exceeded");
                        try {
                            // TODO: close() may block if the remote endpoint doesn't read the data
                            // (eventually there will be a TimeoutException). However, this method
                            // (sendMessage) is intended to run asynchronous code and shouldn't
                            // block. Otherwise it would temporarily stop processing of messages
                            // from other clients.
                            // Maybe call this method on another thread.
                            // Note that when this method is called, the RemoteEndpoint.Async
                            // is still in the process of sending data, so there probably should
                            // be another way to abort the Websocket connection.
                            // Ideally, there should be some abort() method that cancels the
                            // connection immediately...
                            session.close(cr);
                        } catch (IOException e) {
                            // Ignore
                        }

                    } else {

                        // Check if the last message and the new message are
                        // String messages - in that case we concatenate them
                        // to reduce TCP overhead (using ";" as separator).
                        if (msg instanceof StringWebsocketMessage
                                && !messagesToSend.isEmpty()
                                && messagesToSend.getLast()
                                instanceof StringWebsocketMessage) {

                            StringWebsocketMessage ms =
                                    (StringWebsocketMessage) messagesToSend.removeLast();
                            messagesToSendLength -= calculateMessageLength(ms);

                            String concatenated = ms.getString() + ";" +
                                    ((StringWebsocketMessage) msg).getString();
                            msg = new StringWebsocketMessage(concatenated);
                        }

                        messagesToSend.add(msg);
                        messagesToSendLength += calculateMessageLength(msg);
                    }
                } else {
                    isSendingMessage = true;
                    internalSendMessageAsync(msg);
                }
            }

        }
    }

    private long calculateMessageLength(AbstractWebsocketMessage msg) {
        if (msg instanceof BinaryWebsocketMessage) {
            return ((BinaryWebsocketMessage) msg).getBytes().capacity();
        } else if (msg instanceof StringWebsocketMessage) {
            return ((StringWebsocketMessage) msg).getString().length() * 2;
        }

        return 0;
    }

    /**
     * Internally sends the messages asynchronously.
     * @param msg
     */
    private void internalSendMessageAsync(AbstractWebsocketMessage msg) {
        try {
            if (msg instanceof StringWebsocketMessage) {
                StringWebsocketMessage sMsg = (StringWebsocketMessage) msg;
                async.sendText(sMsg.getString(), sendHandler);

            } else if (msg instanceof BinaryWebsocketMessage) {
                BinaryWebsocketMessage bMsg = (BinaryWebsocketMessage) msg;
                async.sendBinary(bMsg.getBytes(), sendHandler);

            } else if (msg instanceof CloseWebsocketMessage) {
                // Close the session.
                session.close();
            }
        } catch (IllegalStateException ex) {
            // Trying to write to the client when the session has
            // already been closed.
            // Ignore
        } catch (IOException ex) {
            // Trying to write to the client when the session has
            // already been closed.
            // Ignore
        }
    }



    /**
     * SendHandler that will continue to send buffered messages.
     */
    private final SendHandler sendHandler = new SendHandler() {
        @Override
        public void onResult(SendResult result) {
            if (!result.isOK()) {
                // Message could not be sent. In this case, we don't
                // set isSendingMessage to false because we must assume the connection
                // broke (and onClose will be called), so we don't try to send
                // other messages.
                // As a precaution, we close the session (e.g. if a send timeout occured).
                // TODO: session.close() blocks, while this handler shouldn't block.
                // Ideally, there should be some abort() method that cancels the
                // connection immediately...
                try {
                    session.close();
                } catch (IOException ex) {
                    // Ignore
                }
            }
            synchronized (messagesToSend) {

                if (!messagesToSend.isEmpty()) {
                    AbstractWebsocketMessage msg = messagesToSend.remove();
                    messagesToSendLength -= calculateMessageLength(msg);

                    internalSendMessageAsync(msg);

                } else {
                    isSendingMessage = false;
                }

            }
        }
    };

}
