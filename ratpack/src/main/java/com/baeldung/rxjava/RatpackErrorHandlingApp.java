package com.baeldung.rxjava;

import ratpack.error.ServerErrorHandler;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Observable;

public class RatpackErrorHandlingApp {

    /**
     * Try hitting http://localhost:5050/error to see the error handler in action
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();
        RatpackServer.start(def -> def.registryOf(regSpec -> regSpec.add(ServerErrorHandler.class, (ctx, throwable) -> {
            ctx.render("Error caught by handler : " + throwable.getMessage());
        }))
            .handlers(chain -> chain.get("error", ctx -> {
                Observable.<String> error(new Exception("Error from observable"))
                    .subscribe(s -> {
                    });
            })));
    }
}
