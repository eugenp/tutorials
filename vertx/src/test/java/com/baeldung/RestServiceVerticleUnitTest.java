package com.baeldung;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.rest.RestServiceVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class RestServiceVerticleUnitTest {

    private Vertx vertx;

    private int port = 8081;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void setup(TestContext testContext) throws IOException {
        vertx = Vertx.vertx();

        // Pick an available and random
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();

        DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject().put("http.port", port));

        vertx.deployVerticle(RestServiceVerticle.class.getName(), options, testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext testContext) {
        vertx.close(testContext.asyncAssertSuccess());
    }

    @Test
    public void givenId_whenReceivedArticle_thenSuccess(TestContext testContext) {
        final Async async = testContext.async();

        vertx.createHttpClient()
            .getNow(port, "localhost", "/api/baeldung/articles/article/12345", response -> {
                response.handler(responseBody -> {
                    testContext.assertTrue(responseBody.toString()
                        .contains("\"id\" : \"12345\""));
                    async.complete();
                });
            });
    }

}
