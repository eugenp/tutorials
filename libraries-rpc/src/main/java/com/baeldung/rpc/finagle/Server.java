package com.baeldung.rpc.finagle;

import com.twitter.finagle.Http;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Service;
import com.twitter.util.Await;
import com.twitter.util.TimeoutException;

public class Server {
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Service service = new LogFilter().andThen(new GreetingService());
        ListeningServer server = Http.serve(":8080", service);
        Await.ready(server);
    }
}
