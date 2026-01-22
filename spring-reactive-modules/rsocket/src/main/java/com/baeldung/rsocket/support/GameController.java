package com.baeldung.rsocket.support;

import static com.baeldung.rsocket.support.Constants.*;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import java.util.List;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class GameController implements Publisher<Payload> {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private final String playerName;
    private final List<Long> shots;
    private Subscriber<? super Payload> subscriber;
    private boolean truce = false;

    public GameController(String playerName) {
        this.playerName = playerName;
        this.shots = generateShotList();
    }

    /**
     * Create a random list of time intervals, 0-1000ms
     *
     * @return List of time intervals
     */
    private List<Long> generateShotList() {
        return Flux.range(1, SHOT_COUNT)
          .map(x -> (long) Math.ceil(Math.random() * 1000))
          .collectList()
          .block();
    }

    @Override
    public void subscribe(Subscriber<? super Payload> subscriber) {
        this.subscriber = subscriber;
        fireAtWill();
    }

    /**
     * Publish game events asynchronously
     */
    private void fireAtWill() {
        new Thread(() -> {
            for (Long shotDelay : shots) {
                try { Thread.sleep(shotDelay); } catch (Exception x) {}
                if (truce) {
                    break;
                }
                LOG.info("{}: bang!", playerName);
                subscriber.onNext(DefaultPayload.create("bang!"));
            }
            if (!truce) {
                LOG.info("{}: I give up!", playerName);
                subscriber.onNext(DefaultPayload.create("I give up"));
            }
            subscriber.onComplete();
        }).start();
    }

    /**
     * Process events from the opponent
     *
     * @param payload Payload received from the rSocket
     */
    public void processPayload(Payload payload) {
        String message = payload.getDataUtf8();
        switch (message) {
            case "bang!":
                String result = Math.random() < 0.5 ? "Haha missed!" : "Ow!";
                LOG.info("{}: {}", playerName, result);
                break;
            case "I give up":
                truce = true;
                LOG.info("{}: OK, truce", playerName);
                break;
        }
    }
}
