package com.baeldung.aeron;

import io.aeron.driver.MediaDriver;
import io.aeron.driver.ThreadingMode;
import org.agrona.concurrent.IdleStrategy;
import org.junit.jupiter.api.Test;

public class MediaDriverLiveTest {
    @Test
    void startDefaultMediaDriver() {
        try (MediaDriver mediaDriver = MediaDriver.launch()) {
            System.out.println("Media Driver: " + mediaDriver.aeronDirectoryName());
        }
    }

    @Test
    void startConfiguredMediaDriver() {
        MediaDriver.Context context = new MediaDriver.Context();
        context.threadingMode(ThreadingMode.SHARED);

        try (MediaDriver mediaDriver = MediaDriver.launch(context)) {
            System.out.println("Media Driver: " + mediaDriver.aeronDirectoryName());
        }
    }

    @Test
    void startDefaultEmbeddedMediaDriver() {
        try (MediaDriver mediaDriver = MediaDriver.launchEmbedded()) {
            System.out.println("Media Driver: " + mediaDriver.aeronDirectoryName());
        }
    }
}
