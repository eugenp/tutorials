package com.baeldung.rsocket;

import static com.baeldung.rsocket.support.Constants.*;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

public class ReqResClient {

    private final RSocket socket;

    public ReqResClient() {
        this.socket = RSocketFactory.connect()
          .transport(TcpClientTransport.create("localhost", TCP_PORT))
          .start()
          .block();
    }

    public String callBlocking(String string) {
        return socket
          .requestResponse(DefaultPayload.create(string))
          .map(Payload::getDataUtf8)
          .onErrorReturn(ERROR_MSG)
          .block();
    }

    public void dispose() {
        this.socket.dispose();
    }
}
