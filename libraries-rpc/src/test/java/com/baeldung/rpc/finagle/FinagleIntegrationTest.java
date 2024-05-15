package com.baeldung.rpc.finagle;

import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.junit.Test;
import scala.runtime.BoxedUnit;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

public class FinagleIntegrationTest {

    private static final int DEFAULT_PORT = 8079;

    @Test
    public void givenServerAndClient_whenRequestSent_thenClientShouldReceiveResponseFromServer() throws Exception {
        // given
        int port = randomPort();
        Service serverService = new LogFilter().andThen(new GreetingService());
        Http.serve(":" + port, serverService);

        Service<Request, Response> clientService = new LogFilter().andThen(Http.newService(":" + port));

        // when
        Request request = Request.apply(Method.Get(), "/?name=John");
        request.host("localhost");
        Future<Response> response = clientService.apply(request);

        // then
        Await.result(response
                .onSuccess(r -> {
                    assertEquals("Hello John", r.getContentString());
                    return BoxedUnit.UNIT;
                })
                .onFailure(r -> {
                    throw new RuntimeException(r);
                })
        );
    }

    private int randomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();

        } catch (IOException e) {
            return DEFAULT_PORT;
        }
    }
}
