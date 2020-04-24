package com.baeldung.rpc.finagle;

import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Method;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Await;
import com.twitter.util.Future;
import scala.runtime.BoxedUnit;

public class Client {
    public static void main(String[] args) throws Exception {
        Service<Request, Response> service = new LogFilter().andThen(Http.newService(":8080"));
        Request request = Request.apply(Method.Get(), "/?name=John");
        request.host("localhost");
        Future<Response> response = service.apply(request);
        Await.result(response
                .onSuccess(r -> {
                    System.out.println(r.getContentString());
                    return BoxedUnit.UNIT;
                }));
    }
}
