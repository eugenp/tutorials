package com.baeldung.spring;

import ratpack.server.RatpackServer;

import static ratpack.spring.Spring.spring;

public class EmbedSpringBootApp {

    public static void main(String[] args) throws Exception {
        RatpackServer.start(server -> server
          .registry(spring(Config.class))
          .handlers(chain -> chain.get(ctx -> ctx.render(ctx
            .get(Content.class)
            .body()))));
    }

}


