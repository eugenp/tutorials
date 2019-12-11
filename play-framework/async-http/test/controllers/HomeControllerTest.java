package controllers;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.concurrent.Futures;
import play.libs.ws.WSClient;
import play.libs.ws.ahc.AhcCurlRequestLogger;
import play.mvc.Result;
import play.mvc.Results;
import play.test.WithServer;

import java.time.Duration;
import java.util.OptionalInt;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.SERVICE_UNAVAILABLE;

public class HomeControllerTest extends WithServer {
    private final Logger log = LoggerFactory.getLogger(HomeControllerTest.class);

    @Override protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test public void givenMultipleRequestsWhenResponseThenLog() throws InterruptedException {
        AtomicInteger completedReqs = new AtomicInteger(0);
        OptionalInt optHttpsPort = testServer.getRunningHttpsPort();
        String url;
        int port;
        if (optHttpsPort.isPresent()) {
            port = optHttpsPort.getAsInt();
            url = "https://localhost:" + port;
        } else {
            port = testServer.getRunningHttpPort().getAsInt();
            url = "http://localhost:" + port;
        }

        WSClient ws = play.test.WSTestClient.newClient(port);
        IntStream.range(0, 100).parallel().forEach(num ->
          ws.url(url)
            .setRequestFilter(new AhcCurlRequestLogger())
            .addHeader("key", "value")
            .addQueryParameter("num", "" + num)
            .get()
            .thenAccept(r -> {
                log.debug("Thread#" + num + " Request complete: Response code = " + r.getStatus() + " | Response: " + r.getBody() + " | Current Time:" + System.currentTimeMillis());
                completedReqs.incrementAndGet();
            })
        );

        log.debug("Waiting for requests to be completed. Current Time: " + System.currentTimeMillis());
        while (completedReqs.get() != 100) {
            Thread.sleep(100);
        }
        log.debug("All requests have been completed. Exiting test.");
    }

    @Test
    public void givenLongResponseWhenTimeoutThenHandle() throws ExecutionException, InterruptedException {
        OptionalInt optHttpsPort = testServer.getRunningHttpsPort();
        String url;
        int port;

        if (optHttpsPort.isPresent()) {
            port = optHttpsPort.getAsInt();
            url = "https://localhost:" + port;
        } else {
            port = testServer.getRunningHttpPort().getAsInt();
            url = "http://localhost:" + port;
        }

        WSClient ws = play.test.WSTestClient.newClient(port);
        Futures futures = app.injector().instanceOf(Futures.class);
        CompletionStage<Result> f = futures.timeout(ws.url(url).setRequestTimeout(Duration.of(1, SECONDS)).get().thenApply(result -> {
            try {
                Thread.sleep(10000L);
                return Results.ok();
            } catch (InterruptedException e) {
                return Results.status(SERVICE_UNAVAILABLE);
            }
        }), 1L, TimeUnit.SECONDS);
        CompletionStage<Object> res = f.handleAsync((result, e) -> {
            if (e != null) {
                log.error("Exception thrown", e);
                return e.getCause();
            } else {
                return result;
            }
        });
        Class<?> clazz = res.toCompletableFuture().get().getClass();
        assertEquals(TimeoutException.class, clazz);
    }
}
