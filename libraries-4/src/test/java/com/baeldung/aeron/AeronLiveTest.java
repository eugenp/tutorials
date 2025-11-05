package com.baeldung.aeron;

import io.aeron.Aeron;
import io.aeron.driver.MediaDriver;
import org.junit.jupiter.api.Test;

public class AeronLiveTest {
    @Test
    void createAeronApi() {
        try (MediaDriver mediaDriver = MediaDriver.launch()) {
            System.out.println("Media driver: " + mediaDriver.aeronDirectoryName());

            try (Aeron aeron = Aeron.connect()) {
                System.out.println("Aeron connected: " + aeron);
            }
        }
    }

    @Test
    void createEmbeddedAeronApi() {
        try (MediaDriver mediaDriver = MediaDriver.launchEmbedded()) {
            System.out.println("Media driver: " + mediaDriver.aeronDirectoryName());

            Aeron.Context ctx = new Aeron.Context();
            ctx.aeronDirectoryName(mediaDriver.aeronDirectoryName());

            try (Aeron aeron = Aeron.connect(ctx)) {
                System.out.println("Aeron connected: " + aeron);
            }
        }
    }

    @Test
    void createMultipleAeronApi() {
        try (MediaDriver mediaDriver = MediaDriver.launchEmbedded()) {
            System.out.println("Media driver: " + mediaDriver.aeronDirectoryName());

            Aeron aeron1 = null;
            Aeron aeron2 = null;

            try {
                Aeron.Context ctx1 = new Aeron.Context();
                ctx1.aeronDirectoryName(mediaDriver.aeronDirectoryName());
                aeron1 = Aeron.connect(ctx1);
                System.out.println("Aeron 1 connected: " + aeron1);

                Aeron.Context ctx2 = new Aeron.Context();
                ctx2.aeronDirectoryName(mediaDriver.aeronDirectoryName());
                aeron2 = Aeron.connect(ctx2);
                System.out.println("Aeron 2 connected: " + aeron2);
            } finally {
                if (aeron1 != null) {
                    aeron1.close();
                }
                if (aeron2 != null) {
                    aeron2.close();
                }
            }
        }
    }
}
