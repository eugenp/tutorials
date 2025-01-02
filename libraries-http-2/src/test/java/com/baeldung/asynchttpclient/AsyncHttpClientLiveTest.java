package com.baeldung.asynchttpclient;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;

import org.asynchttpclient.Dsl;

import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketListener;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;

import org.junit.Test;

public class AsyncHttpClientLiveTest {

    @Test
    public void givenWebSocketClient_tryToConnect() {

        WebSocketUpgradeHandler.Builder upgradeHandlerBuilder = new WebSocketUpgradeHandler.Builder();
        WebSocketUpgradeHandler wsHandler = upgradeHandlerBuilder.addWebSocketListener(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket websocket) {
                // WebSocket connection opened
            }

            @Override
            public void onClose(WebSocket websocket, int code, String reason) {
                // WebSocket connection closed
            }

            @Override
            public void onError(Throwable t) {
                // WebSocket connection error
                assertTrue(t.getMessage().contains("Request timeout"));
            }
        }).build();

        WebSocket WEBSOCKET_CLIENT = null;
        try {
            WEBSOCKET_CLIENT = Dsl.asyncHttpClient().prepareGet("ws://localhost:5590/websocket").addHeader("header_name", "header_value").addQueryParam("key", "value").setRequestTimeout(5000).execute(wsHandler).get();

            if (WEBSOCKET_CLIENT.isOpen()) {
                WEBSOCKET_CLIENT.sendPingFrame();
                WEBSOCKET_CLIENT.sendTextFrame("test message");
                WEBSOCKET_CLIENT.sendBinaryFrame(new byte[] { 't', 'e', 's', 't' });
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (WEBSOCKET_CLIENT != null && WEBSOCKET_CLIENT.isOpen()) {
                WEBSOCKET_CLIENT.sendCloseFrame(200, "OK");
            }
        }
    }
}
