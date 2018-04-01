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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import websocket.drawboard.wsmessages.BinaryWebsocketMessage;
import websocket.drawboard.wsmessages.StringWebsocketMessage;

/**
 * A Room represents a drawboard where a number of
 * users participate.<br><br>
 *
 * Note: Instance methods should only be invoked by calling
 * {@link #invokeAndWait(Runnable)} to ensure access is correctly synchronized.
 */
public final class Room {

    /**
     * Specifies the type of a room message that is sent to a client.<br>
     * Note: Currently we are sending simple string messages - for production
     * apps, a JSON lib should be used for object-level messages.<br><br>
     *
     * The number (single char) will be prefixed to the string when sending
     * the message.
     */
    public static enum MessageType {
        /**
         * '0': Error: contains error message.
         */
        ERROR('0'),
        /**
         * '1': DrawMessage: contains serialized DrawMessage(s) prefixed
         *      with the current Player's {@link Player#lastReceivedMessageId}
         *      and ",".<br>
         *      Multiple draw messages are concatenated with "|" as separator.
         */
        DRAW_MESSAGE('1'),
        /**
         * '2': ImageMessage: Contains number of current players in this room.
         *      After this message a Binary Websocket message will follow,
         *      containing the current Room image as PNG.<br>
         *      This is the first message that a Room sends to a new Player.
         */
        IMAGE_MESSAGE('2'),
        /**
         * '3': PlayerChanged: contains "+" or "-" which indicate a player
         *      was added or removed to this Room.
         */
        PLAYER_CHANGED('3');

        private final char flag;

        private MessageType(char flag) {
            this.flag = flag;
        }

    }


    /**
     * The lock used to synchronize access to this Room.
     */
    private final ReentrantLock roomLock = new ReentrantLock();

    /**
     * Indicates if this room has already been shutdown.
     */
    private volatile boolean closed = false;

    /**
     * If <code>true</code>, outgoing DrawMessages will be buffered until the
     * drawmessageBroadcastTimer ticks. Otherwise they will be sent
     * immediately.
     */
    private static final boolean BUFFER_DRAW_MESSAGES = true;

    /**
     * A timer which sends buffered drawmessages to the client at once
     * at a regular interval, to avoid sending a lot of very small
     * messages which would cause TCP overhead and high CPU usage.
     */
    private final Timer drawmessageBroadcastTimer = new Timer();

    private static final int TIMER_DELAY = 30;

    /**
     * The current active broadcast timer task. If null, then no Broadcast task is scheduled.
     * The Task will be scheduled if the first player enters the Room, and
     * cancelled if the last player exits the Room, to avoid unnecessary timer executions.
     */
    private TimerTask activeBroadcastTimerTask;


    /**
     * The current image of the room drawboard. DrawMessages that are
     * received from Players will be drawn onto this image.
     */
    private final BufferedImage roomImage =
            new BufferedImage(900, 600, BufferedImage.TYPE_INT_RGB);
    private final Graphics2D roomGraphics = roomImage.createGraphics();


    /**
     * The maximum number of players that can join this room.
     */
    private static final int MAX_PLAYER_COUNT = 100;

    /**
     * List of all currently joined players.
     */
    private final List<Player> players = new ArrayList<Player>();



    public Room() {
        roomGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the image with white background.
        roomGraphics.setBackground(Color.WHITE);
        roomGraphics.clearRect(0, 0, roomImage.getWidth(),
                roomImage.getHeight());
    }

    private TimerTask createBroadcastTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        broadcastTimerTick();
                    }
                });
            }
        };
    }

    /**
     * Creates a Player from the given Client and adds it to this room.
     * @param client the client
     */
    public Player createAndAddPlayer(Client client) {
        if (players.size() >= MAX_PLAYER_COUNT) {
            throw new IllegalStateException("Maximum player count ("
                    + MAX_PLAYER_COUNT + ") has been reached.");
        }

        Player p = new Player(this, client);

        // Broadcast to the other players that one player joined.
        broadcastRoomMessage(MessageType.PLAYER_CHANGED, "+");

        // Add the new player to the list.
        players.add(p);

        // If currently no Broadcast Timer Task is scheduled, then we need to create one.
        if (activeBroadcastTimerTask == null) {
            activeBroadcastTimerTask = createBroadcastTimerTask();
            drawmessageBroadcastTimer.schedule(activeBroadcastTimerTask,
                    TIMER_DELAY, TIMER_DELAY);
        }

        // Send him the current number of players and the current room image.
        String content = String.valueOf(players.size());
        p.sendRoomMessage(MessageType.IMAGE_MESSAGE, content);

        // Store image as PNG
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            ImageIO.write(roomImage, "PNG", bout);
        } catch (IOException e) { /* Should never happen */ }


        // Send the image as binary message.
        BinaryWebsocketMessage msg = new BinaryWebsocketMessage(
                ByteBuffer.wrap(bout.toByteArray()));
        p.getClient().sendMessage(msg);

        return p;

    }

    /**
     * @see Player#removeFromRoom()
     * @param p
     */
    private void internalRemovePlayer(Player p) {
        boolean removed = players.remove(p);
        assert removed;

        // If the last player left the Room, we need to cancel the Broadcast Timer Task.
        if (players.size() == 0) {
            // Cancel the task.
            // Note that it can happen that the TimerTask is just about to execute (from
            // the Timer thread) but waits until all players are gone (or even until a new
            // player is added to the list), and then executes. This is OK. To prevent it,
            // a TimerTask subclass would need to have some boolean "cancel" instance variable and
            // query it in the invocation of Room#invokeAndWait.
            activeBroadcastTimerTask.cancel();
            activeBroadcastTimerTask = null;
        }

        // Broadcast that one player is removed.
        broadcastRoomMessage(MessageType.PLAYER_CHANGED, "-");
    }

    /**
     * @see Player#handleDrawMessage(DrawMessage, long)
     * @param p
     * @param msg
     * @param msgId
     */
    private void internalHandleDrawMessage(Player p, DrawMessage msg,
            long msgId) {
        p.setLastReceivedMessageId(msgId);

        // Draw the RoomMessage onto our Room Image.
        msg.draw(roomGraphics);

        // Broadcast the Draw Message.
        broadcastDrawMessage(msg);
    }


    /**
     * Broadcasts the given drawboard message to all connected players.<br>
     * Note: For DrawMessages, please use
     * {@link #broadcastDrawMessage(DrawMessage)}
     * as this method will buffer them and prefix them with the correct
     * last received Message ID.
     * @param type
     * @param content
     */
    private void broadcastRoomMessage(MessageType type, String content) {
        for (Player p : players) {
            p.sendRoomMessage(type, content);
        }
    }


    /**
     * Broadcast the given DrawMessage. This will buffer the message
     * and the {@link #drawmessageBroadcastTimer} will broadcast them
     * at a regular interval, prefixing them with the player's current
     * {@link Player#lastReceivedMessageId}.
     * @param msg
     */
    private void broadcastDrawMessage(DrawMessage msg) {
        if (!BUFFER_DRAW_MESSAGES) {
            String msgStr = msg.toString();

            for (Player p : players) {
                String s = String.valueOf(p.getLastReceivedMessageId())
                        + "," + msgStr;
                p.sendRoomMessage(MessageType.DRAW_MESSAGE, s);
            }
        } else {
            for (Player p : players) {
                p.getBufferedDrawMessages().add(msg);
            }
        }
    }


    /**
     * Tick handler for the broadcastTimer.
     */
    private void broadcastTimerTick() {
        // For each Player, send all per Player buffered
        // DrawMessages, prefixing each DrawMessage with the player's
        // lastReceivedMessageId.
        // Multiple messages are concatenated with "|".

        for (Player p : players) {

            StringBuilder sb = new StringBuilder();
            List<DrawMessage> drawMessages = p.getBufferedDrawMessages();

            if (drawMessages.size() > 0) {
                for (int i = 0; i < drawMessages.size(); i++) {
                    DrawMessage msg = drawMessages.get(i);

                    String s = String.valueOf(p.getLastReceivedMessageId())
                            + "," + msg.toString();
                    if (i > 0)
                        sb.append("|");

                    sb.append(s);
                }
                drawMessages.clear();

                p.sendRoomMessage(MessageType.DRAW_MESSAGE, sb.toString());
            }
        }
    }

    /**
     * A list of cached {@link Runnable}s to prevent recursive invocation of Runnables
     * by one thread. This variable is only used by one thread at a time and then
     * set to <code>null</code>.
     */
    private List<Runnable> cachedRunnables = null;

    /**
     * Submits the given Runnable to the Room Executor and waits until it
     * has been executed. Currently, this simply means that the Runnable
     * will be run directly inside of a synchronized() block.<br>
     * Note that if a runnable recursively calls invokeAndWait() with another
     * runnable on this Room, it will not be executed recursively, but instead
     * cached until the original runnable is finished, to keep the behavior of
     * using a Executor.
     * @param task
     */
    public void invokeAndWait(Runnable task)  {

        // Check if the current thread already holds a lock on this room.
        // If yes, then we must not directly execute the Runnable but instead
        // cache it until the original invokeAndWait() has finished.
        if (roomLock.isHeldByCurrentThread()) {

            if (cachedRunnables == null) {
                cachedRunnables = new ArrayList<Runnable>();
            }
            cachedRunnables.add(task);

        } else {

            roomLock.lock();
            try {
                // Explicitly overwrite value to ensure data consistency in
                // current thread
                cachedRunnables = null;

                if (!closed) {
                    task.run();
                }

                // Run the cached runnables.
                if (cachedRunnables != null) {
                    for (int i = 0; i < cachedRunnables.size(); i++) {
                        if (!closed) {
                            cachedRunnables.get(i).run();
                        }
                    }
                    cachedRunnables = null;
                }

            } finally {
                roomLock.unlock();
            }

        }

    }

    /**
     * Shuts down the roomExecutor and the drawmessageBroadcastTimer.
     */
    public void shutdown() {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                closed = true;
                drawmessageBroadcastTimer.cancel();
                roomGraphics.dispose();
            }
        });
    }


    /**
     * A Player participates in a Room. It is the interface between the
     * {@link Room} and the {@link Client}.<br><br>
     *
     * Note: This means a player object is actually a join between Room and
     * Client.
     */
    public final class Player {

        /**
         * The room to which this player belongs.
         */
        private Room room;

        /**
         * The room buffers the last draw message ID that was received from
         * this player.
         */
        private long lastReceivedMessageId = 0;

        private final Client client;

        /**
         * Buffered DrawMessages that will be sent by a Timer.
         */
        private final List<DrawMessage> bufferedDrawMessages =
                new ArrayList<DrawMessage>();

        private List<DrawMessage> getBufferedDrawMessages() {
            return bufferedDrawMessages;
        }

        private Player(Room room, Client client) {
            this.room = room;
            this.client = client;
        }

        public Room getRoom() {
            return room;
        }

        public Client getClient() {
            return client;
        }

        /**
         * Removes this player from its room, e.g. when
         * the client disconnects.
         */
        public void removeFromRoom() {
            if (room != null) {
                room.internalRemovePlayer(this);
                room = null;
            }
        }


        private long getLastReceivedMessageId() {
            return lastReceivedMessageId;
        }
        private void setLastReceivedMessageId(long value) {
            lastReceivedMessageId = value;
        }


        /**
         * Handles the given DrawMessage by drawing it onto this Room's
         * image and by broadcasting it to the connected players.
         * @param msg
         * @param msgId
         */
        public void handleDrawMessage(DrawMessage msg, long msgId) {
            room.internalHandleDrawMessage(this, msg, msgId);
        }


        /**
         * Sends the given room message.
         * @param type
         * @param content
         */
        private void sendRoomMessage(MessageType type, String content) {
            if (content == null || type == null)
                throw new NullPointerException();

            String completeMsg = String.valueOf(type.flag) + content;

            client.sendMessage(new StringWebsocketMessage(completeMsg));
        }
    }
}
