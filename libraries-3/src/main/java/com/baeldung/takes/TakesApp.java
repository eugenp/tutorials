package com.baeldung.takes;

import java.io.IOException;
import java.sql.SQLException;

import org.takes.Response;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.RqFallback;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.misc.Opt;
import org.takes.rs.RsText;

public final class TakesApp {

    public static void main(final String... args) throws IOException, SQLException {
        new FtBasic(
            new TkFallback(
                new TkFork(
                    new FkRegex("/", new TakesHelloWorld()),
                    new FkRegex("/index", new TakesIndex()),
                    new FkRegex("/contact", new TakesContact())
                    ),
                new FbChain(
                    new FbStatus(404, new RsText("Page Not Found")),
                    new FbStatus(405, new RsText("Method Not Allowed")),
                    new Fallback() {
                        @Override
                        public Opt<Response> route(final RqFallback req) {
                            return new Opt.Single<Response>(new RsText(req.throwable().getMessage()));
                        }
                    })
                ), 6060
            ).start(Exit.NEVER);
    }

}