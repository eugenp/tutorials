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

import java.io.EOFException;
import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import websocket.drawboard.DrawMessage.ParseException;
import websocket.drawboard.wsmessages.StringWebsocketMessage;


public final class DrawboardEndpoint extends Endpoint {

    private static final Log log =
            LogFactory.getLog(DrawboardEndpoint.class);


    /**
     * Our room where players can join.
     */
    private static volatile Room room = null;
    private static final Object roomLock = new Object();

    public static Room getRoom(boolean create) {
        if (create) {
            if (room == null) {
                synchronized (roomLock) {
                    if (room == null) {
                        room = new Room();
                    }
                }
            }
            return room;
        } else {
            return room;
        }
    }

    /**
     * The player that is associated with this Endpoint and the current room.
     * Note that this variable is only accessed from the Room Thread.<br><br>
     *
     * TODO: Currently, Tomcat uses an Endpoint instance once - however
     * the java doc of endpoint says:
     * "Each instance of a websocket endpoint is guaranteed not to be called by
     * more than one thread at a time per active connection."
     * This could mean that after calling onClose(), the instance
     * could be reused for another connection so onOpen() will get called
     * (possibly from another thread).<br>
     * If this is the case, we would need a variable holder for the variables
     * that are accessed by the Room thread, and read the reference to the holder
     * at the beginning of onOpen, onMessage, onClose methods to ensure the room
     * thread always gets the correct instance of the variable holder.
     */
    private Room.Player player;


    @Override
    public void onOpen(Session session, EndpointConfig config) {
        // Set maximum messages size to 10.000 bytes.
        session.setMaxTextMessageBufferSize(10000);
        session.addMessageHandler(stringHandler);
        final Client client = new Client(session);

        final Room room = getRoom(true);
        room.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {

                    // Create a new Player and add it to the room.
                    try {
                        player = room.createAndAddPlayer(client);
                    } catch (IllegalStateException ex) {
                        // Probably the max. number of players has been
                        // reached.
                        client.sendMessage(new StringWebsocketMessage(
                                "0" + ex.getLocalizedMessage()));
                        // Close the connection.
                        client.close();
                    }

                } catch (RuntimeException ex) {
                    log.error("Unexpected exception: " + ex.toString(), ex);
                }
            }
        });

    }


    @Override
    public void onClose(Session session, CloseReason closeReason) {
        Room room = getRoom(false);
        if (room != null) {
            room.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Player can be null if it couldn't enter the room
                        if (player != null) {
                            // Remove this player from the room.
                            player.removeFromRoom();

                            // Set player to null to prevent NPEs when onMessage events
                            // are processed (from other threads) after onClose has been
                            // called from different thread which closed the Websocket session.
                            player = null;
                        }
                    } catch (RuntimeException ex) {
                        log.error("Unexpected exception: " + ex.toString(), ex);
                    }
                }
            });
        }
    }



    @Override
    public void onError(Session session, Throwable t) {
        // Most likely cause is a user closing their browser. Check to see if
        // the root cause is EOF and if it is ignore it.
        // Protect against infinite loops.
        int count = 0;
        Throwable root = t;
        while (root.getCause() != null && count < 20) {
            root = root.getCause();
            count ++;
        }
        if (root instanceof EOFException) {
            // Assume this is triggered by the user closing their browser and
            // ignore it.
        } else if (!session.isOpen() && root instanceof IOException) {
            // IOException after close. Assume this is a variation of the user
            // closing their browser (or refreshing very quickly) and ignore it.
        } else {
            log.error("onError: " + t.toString(), t);
        }
    }



    private final MessageHandler.Whole<String> stringHandler =
            new MessageHandler.Whole<String>() {

        @Override
        public void onMessage(final String message) {
            // Invoke handling of the message in the room.
            room.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {

                        // Currently, the only types of messages the client will send
                        // are draw messages prefixed by a Message ID
                        // (starting with char '1'), and pong messages (starting
                        // with char '0').
                        // Draw messages should look like this:
                        // ID|type,colR,colB,colG,colA,thickness,x1,y1,x2,y2,lastInChain

                        boolean dontSwallowException = false;
                        try {
                            char messageType = message.charAt(0);
                            String messageContent = message.substring(1);
                            switch (messageType) {
                            case '0':
                                // Pong message.
                                // Do nothing.
                                break;

                            case '1':
                                // Draw message
                                int indexOfChar = messageContent.indexOf('|');
                                long msgId = Long.parseLong(
                                        messageContent.substring(0, indexOfChar));

                                DrawMessage msg = DrawMessage.parseFromString(
                                        messageContent.substring(indexOfChar + 1));

                                // Don't ignore RuntimeExceptions thrown by
                                // this method
                                // TODO: Find a better solution than this variable
                                dontSwallowException = true;
                                if (player != null) {
                                    player.handleDrawMessage(msg, msgId);
                                }
                                dontSwallowException = false;

                                break;
                            }

                        } catch (RuntimeException ex) {
                            // Client sent invalid data.
                            // Ignore, TODO: maybe close connection
                            if (dontSwallowException) {
                                throw ex;
                            }
                        } catch (ParseException ex) {
                            // Client sent invalid data.
                            // Ignore, TODO: maybe close connection
                        }
                    } catch (RuntimeException ex) {
                        log.error("Unexpected exception: " + ex.toString(), ex);
                    }
                }
            });

        }
    };


}
