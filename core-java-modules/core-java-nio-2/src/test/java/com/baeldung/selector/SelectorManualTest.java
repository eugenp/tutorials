package com.baeldung.selector;

import org.junit.Test;
import java.io.IOException;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

import static java.nio.channels.SelectionKey.OP_READ;

public class SelectorManualTest {

    @Test
    public void whenWakeUpCalledOnSelector_thenBlockedThreadReturns() throws IOException, InterruptedException {
        Pipe pipe = Pipe.open();

        Selector selector = Selector.open();
        SelectableChannel channel = pipe.source();
        channel.configureBlocking(false);
        channel.register(selector, OP_READ);

        List<String> invocationStepsTracker = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch latch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            invocationStepsTracker.add(">> Count down");
            latch.countDown();
            try {
                invocationStepsTracker.add(">> Start select");
                selector.select();
                invocationStepsTracker.add(">> End select");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        invocationStepsTracker.add(">> Start await");
        thread.start();
        latch.await();
        invocationStepsTracker.add(">> End await");

        invocationStepsTracker.add(">> Wakeup thread");

        selector.wakeup();
        channel.close();

        assertThat(invocationStepsTracker)
          .containsExactly(
            ">> Start await",
            ">> Count down",
            ">> Start select",
            ">> End await",
            ">> Wakeup thread",
            ">> End select"
          );
    }
}
