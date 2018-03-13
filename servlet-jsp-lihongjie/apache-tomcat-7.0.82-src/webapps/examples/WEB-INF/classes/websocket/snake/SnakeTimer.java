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
package websocket.snake;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Sets up the timer for the multi-player snake game WebSocket example.
 */
public class SnakeTimer {

    private static final Log log =
            LogFactory.getLog(SnakeTimer.class);

    private static Timer gameTimer = null;

    private static final long TICK_DELAY = 100;

    private static final ConcurrentHashMap<Integer, Snake> snakes =
            new ConcurrentHashMap<Integer, Snake>();

    protected static synchronized void addSnake(Snake snake) {
        if (snakes.size() == 0) {
            startTimer();
        }
        snakes.put(Integer.valueOf(snake.getId()), snake);
    }


    protected static Collection<Snake> getSnakes() {
        return Collections.unmodifiableCollection(snakes.values());
    }


    protected static synchronized void removeSnake(Snake snake) {
        snakes.remove(Integer.valueOf(snake.getId()));
        if (snakes.size() == 0) {
            stopTimer();
        }
    }


    protected static void tick() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = SnakeTimer.getSnakes().iterator();
                iterator.hasNext();) {
            Snake snake = iterator.next();
            snake.update(SnakeTimer.getSnakes());
            sb.append(snake.getLocationsJson());
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'update', 'data' : [%s]}",
                sb.toString()));
    }

    protected static void broadcast(String message) {
        for (Snake snake : SnakeTimer.getSnakes()) {
            try {
                snake.sendMessage(message);
            } catch (IllegalStateException ise) {
                // An ISE can occur if an attempt is made to write to a
                // WebSocket connection after it has been closed. The
                // alternative to catching this exception is to synchronise
                // the writes to the clients along with the addSnake() and
                // removeSnake() methods that are already synchronised.
            }
        }
    }


    public static void startTimer() {
        gameTimer = new Timer(SnakeTimer.class.getSimpleName() + " Timer");
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


    public static void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }
}
