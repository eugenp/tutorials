package controllers;

import java.util.concurrent.ExecutionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.concurrent.Futures;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.libs.ws.ahc.AhcCurlRequestLogger;
import play.mvc.Result;
import play.mvc.Results;
import play.test.WithServer;

import java.time.Duration;
import java.util.OptionalInt;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.SERVICE_UNAVAILABLE;

import play.mvc.Http.MultipartFormData.*;
import akka.stream.javadsl.*;
import akka.util.ByteString;

public class HomeControllerTest extends WithServer {

    private final Logger log = LoggerFactory.getLogger(HomeControllerTest.class);
    private String url;
    private int port;

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Before
    public void setup() {
        OptionalInt optHttpsPort = testServer.getRunningHttpsPort();
        if (optHttpsPort.isPresent()) {
            port = optHttpsPort.getAsInt();
            url = "https://localhost:" + port;
        } else {
            port = testServer.getRunningHttpPort().getAsInt();
            url = "http://localhost:" + port;
        }
    }

    @Test
    public void givenASingleGetRequestWhenResponseThenBlockWithCompletableAndLog()
      throws InterruptedException, ExecutionException {
        AtomicInteger completedReqs = new AtomicInteger(0);
        WSClient ws = play.test.WSTestClient.newClient(port);
        WSResponse wsResponse = ws.url(url)
                                  .setRequestFilter(new AhcCurlRequestLogger())
                                  .addHeader("key", "value")
                                  .addQueryParameter("num", "" + 1)
                                  .get()
                                  .toCompletableFuture()
                                  .get();

        log.debug("Thread#" + Thread.currentThread()
                                    .getId() + " Request complete: Response code = "
          + wsResponse.getStatus()
          + " | Response: " + wsResponse.getBody() + " | Current Time:"
          + System.currentTimeMillis());
    }

    @Test
    public void givenASingleGetRequestWhenResponseThenLog() throws InterruptedException {
        AtomicInteger completedReqs = new AtomicInteger(0);
        WSClient ws = play.test.WSTestClient.newClient(port);
        ws.url(url)
          .setRequestFilter(new AhcCurlRequestLogger())
          .addHeader("key", "value")
          .addQueryParameter("num", "" + 1)
          .get()
          .thenAccept(r -> {
              log.debug("Thread#" + Thread.currentThread()
                                          .getId() + " Request complete: Response code = "
                + r.getStatus()
                + " | Response: " + r.getBody() + " | Current Time:" + System.currentTimeMillis());
              completedReqs.incrementAndGet();
          });

        log.debug(
          "Waiting for requests to be completed. Current Time: " + System.currentTimeMillis());
        while (completedReqs.get() != 1) {
            Thread.sleep(100);
        }
        log.debug("All requests have been completed. Exiting test.");
    }

    @Test
    public void givenASinglePostRequestWhenResponseThenLog() throws InterruptedException {
        AtomicInteger completedReqs = new AtomicInteger(0);
        WSClient ws = play.test.WSTestClient.newClient(port);
        ws.url(url)
          .setContentType("application/x-www-form-urlencoded")
          .post("key1=value1&key2=value2")
          .thenAccept(r -> {
              log.debug("Thread#" + Thread.currentThread()
                                          .getId() + " Request complete: Response code = "
                + r.getStatus()
                + " | Response: " + r.getBody() + " | Current Time:" + System.currentTimeMillis());
              completedReqs.incrementAndGet();
          });

        log.debug(
          "Waiting for requests to be completed. Current Time: " + System.currentTimeMillis());
        while (completedReqs.get() != 1) {
            Thread.sleep(100);
        }
        log.debug("All requests have been completed. Exiting test.");
    }

    @Test
    public void givenMultipleRequestsWhenResponseThenLog() throws InterruptedException {
        AtomicInteger completedReqs = new AtomicInteger(0);
        WSClient ws = play.test.WSTestClient.newClient(port);
        IntStream.range(0, 100)
                 .parallel()
                 .forEach(num ->
                   ws.url(url)
                     .setRequestFilter(new AhcCurlRequestLogger())
                     .addHeader("key", "value")
                     .addQueryParameter("num", "" + num)
                     .get()
                     .thenAccept(r -> {
                         log.debug(
                           "Thread#" + num + " Request complete: Response code = " + r.getStatus()
                             + " | Response: " + r.getBody() + " | Current Time:"
                             + System.currentTimeMillis());
                         completedReqs.incrementAndGet();
                     })
                 );

        log.debug(
          "Waiting for requests to be completed. Current Time: " + System.currentTimeMillis());
        while (completedReqs.get() != 100) {
            Thread.sleep(100);
        }
        log.debug("All requests have been completed. Exiting test.");
    }

    @Test
    public void givenLongResponseWhenTimeoutThenHandle() throws InterruptedException {
        WSClient ws = play.test.WSTestClient.newClient(port);
        Futures futures = app.injector()
                             .instanceOf(Futures.class);
        CompletionStage<Result> f = futures.timeout(ws.url(url)
                                                      .setRequestTimeout(Duration.of(1, SECONDS))
                                                      .get()
                                                      .thenApply(result -> {
                                                          try {
                                                              Thread.sleep(2000L);
                                                              return Results.ok();
                                                          } catch (InterruptedException e) {
                                                              return Results.status(
                                                                SERVICE_UNAVAILABLE);
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
        res.thenAccept(result ->
          assertEquals(TimeoutException.class, result)
        );
        Thread.sleep(2500);
    }
}
