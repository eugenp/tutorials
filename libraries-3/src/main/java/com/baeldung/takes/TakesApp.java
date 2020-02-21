package com.baeldung.takes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.takes.Request;
import org.takes.Response;
import org.takes.facets.fallback.Fallback;
import org.takes.facets.fallback.FbChain;
import org.takes.facets.fallback.FbStatus;
import org.takes.facets.fallback.RqFallback;
import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.Fork;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;
import org.takes.rs.RsText;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;
import org.takes.rs.RsWithType;
import org.takes.tk.TkSlf4j;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class TakesApp {

    //    public static void main(final String... args) throws Exception {
    //        new FtBasic(new TkFork(
    //            new FkRegex("/", "Hello, World!"),
    //            new FkRegex("/index", new TakesIndex()),
    //            new FkRegex("/contact", new TakesContact())
    //            ),6060).start(Exit.NEVER);
    //    }

    public static void main(final String... args) throws IOException, SQLException {
        new FtBasic(
            new TkFallback(
                new TkSlf4j(
                    new TkFork(
                        new FkRegex("/", "Hello, World!"),
                        new FkRegex("/index", new TakesIndex()),
                        new FkRegex("/contact", new TakesContact()),
                        new FkRegex("\\A/createUser", new TakesCreateUser(TakesApp.dbConnection())),
                        new FkRegex("\\A/readUser", new TakesReadUser(TakesApp.dbConnection()))
                        )
                    ),
                new FbChain(
                    new FbStatus(404, new RsText("sorry, page is absent")),
                    new FbStatus(405, new RsText("this method is not allowed here")),
                    new Fallback() {
                        @Override
                        public Opt<Response> route(final RqFallback req) {
                            return new Opt.Single<Response>(
                                new RsText(req.throwable().getMessage())
                                );
                        }
                    })
                ),
            6060
            ).start(Exit.NEVER);
    }


    //    public static void main(final String... args) throws IOException {
    //
    //        new FtBasic( 
    //            new TkFork(
    //                new Fork() {
    //                    @Override
    //                    public Opt<Response> route(Request req) throws Exception {
    //                        if (new RqHref.Base(req).href().path().equals("/entry")) {
    //                            return new Opt.Single<Response>(new RsWithStatus(new RsWithType(new RsWithBody("hello this is for request"), "text/html"), 200));
    //                        } else {
    //                            return new Opt.Single<Response>(null);
    //                        }
    //                    }
    //                }
    //                ),6060).start(Exit.NEVER);
    //
    //
    //    }

    private static Connection dbConnection() throws SQLException {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:h2:mem:devDB;DB_CLOSE_DELAY=-1;INIT=runscript from 'classpath:/db.sql'");
        config.setUsername("sa");
        config.setPassword("");

        @SuppressWarnings("resource")
        HikariDataSource ds = new HikariDataSource(config);

        return ds.getConnection();
    }

}