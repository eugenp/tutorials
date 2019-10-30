package com.baeldung.blade.sample;

import com.baeldung.blade.sample.interceptors.BaeldungMiddleware;
import com.blade.Blade;
import com.blade.event.EventType;
import com.blade.mvc.WebContext;
import com.blade.mvc.http.Session;

public class App {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        Blade.of()
            .get("/", ctx -> ctx.render("index.html"))
            .get("/basic-route-example", ctx -> ctx.text("GET called"))
            .post("/basic-route-example", ctx -> ctx.text("POST called"))
            .put("/basic-route-example", ctx -> ctx.text("PUT called"))
            .delete("/basic-route-example", ctx -> ctx.text("DELETE called"))
            .addStatics("/custom-static")
            // .showFileList(true)
            .enableCors(true)
            .before("/user/*", ctx -> log.info("[NarrowedHook] Before '/user/*', URL called: " + ctx.uri()))
            .on(EventType.SERVER_STARTED, e -> {
                String version = WebContext.blade()
                    .env("app.version")
                    .orElse("N/D");
                log.info("[Event::serverStarted] Loading 'app.version' from configuration, value: " + version);
            })
            .on(EventType.SESSION_CREATED, e -> {
                Session session = (Session) e.attribute("session");
                session.attribute("mySessionValue", "Baeldung");
            })
            .use(new BaeldungMiddleware())
            .start(App.class, args);
    }
}
