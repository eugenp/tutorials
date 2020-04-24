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

import static org.junit.Assert.assertEquals;

public class ClientServerTest {
    @Test
    public void clientShouldReceiveResponseFromServer() throws Exception {
        // given
        Service serverService = new LogFilter().andThen(new GreetingService());
        Http.serve(":8080", serverService);

        Service<Request, Response> clientService = new LogFilter().andThen(Http.newService(":8080"));

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
}
