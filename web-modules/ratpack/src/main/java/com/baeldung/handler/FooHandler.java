package com.baeldung.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class FooHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        ctx.getResponse()
            .send("Hello Foo!");
    }
}
