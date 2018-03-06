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
package websocket.tc7.snake;

import java.awt.Color;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Example web socket servlet for simple multi-player snake.
 * @deprecated See {@link websocket.snake.SnakeAnnotation}
 */
@Deprecated
public class SnakeWebSocketServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    private static final Log log =
            LogFactory.getLog(SnakeWebSocketServlet.class);

    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;

    private static final long TICK_DELAY = 100;

    private static final Random random = new Random();

    private final Timer gameTimer =
            new Timer(SnakeWebSocketServlet.class.getSimpleName() + " Timer");

    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, Snake> snakes =
            new ConcurrentHashMap<Integer, Snake>();
    private final ConcurrentHashMap<Integer, SnakeMessageInbound> connections =
            new ConcurrentHashMap<Integer, SnakeMessageInbound>();

    @Override
    public void init() throws ServletException {
        super.init();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (RuntimeException e) {
                    log.error("Caught to prevent timer from shutting down", e);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }

    private void tick() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = getSnakes().iterator();
                iterator.hasNext();) {
            Snake snake = iterator.next();
            snake.update(getSnakes());
            sb.append(snake.getLocationsJson());
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'update', 'data' : [%s]}",
                sb.toString()));
    }

    private void broadcast(String message) {
        for (SnakeMessageInbound connection : getConnections()) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }
    }

    private Collection<SnakeMessageInbound> getConnections() {
        return Collections.unmodifiableCollection(connections.values());
    }

    private Collection<Snake> getSnakes() {
        return Collections.unmodifiableCollection(snakes.values());
    }

    public static String getRandomHexColor() {
        float hue = random.nextFloat();
        // sat between 0.1 and 0.3
        float saturation = (random.nextInt(2000) + 1000) / 10000f;
        float luminance = 0.9f;
        Color color = Color.getHSBColor(hue, saturation, luminance);
        return '#' + Integer.toHexString(
                (color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }

    public static Location getRandomLocation() {
        int x = roundByGridSize(
                random.nextInt(SnakeWebSocketServlet.PLAYFIELD_WIDTH));
        int y = roundByGridSize(
                random.nextInt(SnakeWebSocketServlet.PLAYFIELD_HEIGHT));
        return new Location(x, y);
    }

    private static int roundByGridSize(int value) {
        value = value + (SnakeWebSocketServlet.GRID_SIZE / 2);
        value = value / SnakeWebSocketServlet.GRID_SIZE;
        value = value * SnakeWebSocketServlet.GRID_SIZE;
        return value;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new SnakeMessageInbound(connectionIds.incrementAndGet());
    }

    private final class SnakeMessageInbound extends MessageInbound {

        private final int id;
        private Snake snake;

        private SnakeMessageInbound(int id) {
            this.id = id;
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            this.snake = new Snake(id, outbound);
            snakes.put(Integer.valueOf(id), snake);
            connections.put(Integer.valueOf(id), this);
            StringBuilder sb = new StringBuilder();
            for (Iterator<Snake> iterator = getSnakes().iterator();
                    iterator.hasNext();) {
                Snake snake = iterator.next();
                sb.append(String.format("{id: %d, color: '%s'}",
                        Integer.valueOf(snake.getId()), snake.getHexColor()));
                if (iterator.hasNext()) {
                    sb.append(',');
                }
            }
            broadcast(String.format("{'type': 'join','data':[%s]}",
                    sb.toString()));
        }

        @Override
        protected void onClose(int status) {
            connections.remove(Integer.valueOf(id));
            snakes.remove(Integer.valueOf(id));
            broadcast(String.format("{'type': 'leave', 'id': %d}",
                    Integer.valueOf(id)));
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException(
                    "Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer charBuffer) throws IOException {
            String message = charBuffer.toString();
            if ("west".equals(message)) {
                snake.setDirection(Direction.WEST);
            } else if ("north".equals(message)) {
                snake.setDirection(Direction.NORTH);
            } else if ("east".equals(message)) {
                snake.setDirection(Direction.EAST);
            } else if ("south".equals(message)) {
                snake.setDirection(Direction.SOUTH);
            }
        }
    }
}
