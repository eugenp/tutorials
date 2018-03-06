/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package websocket.tc7.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 * @deprecated See {@link websocket.echo.EchoAnnotation}
 */
@Deprecated
public class EchoStream extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new EchoStreamInbound();
    }

    private static final class EchoStreamInbound extends StreamInbound {

        @Override
        protected void onBinaryData(InputStream is) throws IOException {
            // Simply echo the data to back to the client.
            WsOutbound outbound = getWsOutbound();

            int i = is.read();
            while (i != -1) {
                outbound.writeBinaryData(i);
                i = is.read();
            }

            outbound.flush();
        }

        @Override
        protected void onTextData(Reader r) throws IOException {
            // Simply echo the data to back to the client.
            WsOutbound outbound = getWsOutbound();

            int c = r.read();
            while (c != -1) {
                outbound.writeTextData((char) c);
                c = r.read();
            }

            outbound.flush();
        }
    }
}
