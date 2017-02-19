package com.baeldung.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.baeldung.model.Message;
import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public String encode(Message message) throws EncodeException {
        System.out.println("converting message obj to json format");
        Gson gson = new Gson();
        String json = gson.toJson(message);
        return json;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
