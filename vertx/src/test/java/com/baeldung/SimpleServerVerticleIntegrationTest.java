package com.baeldung;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SimpleServerVerticleIntegrationTest {
    private Vertx vertx;

    @Before
    public void setup(TestContext testContext) {
        vertx = Vertx.vertx();

        vertx.deployVerticle(SimpleServerVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void whenReceivedResponse_thenSuccess(TestContext testContext) {
        final Async async = testContext.async();

        vertx
          .createHttpClient()
          .getNow(8080, "localhost", "/",
            response -> response.handler(responseBody -> {
              testContext.assertTrue(responseBody
                .toString()
                .contains("Welcome"));
              async.complete();
          }));
    }

}

