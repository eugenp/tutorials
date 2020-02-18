package com.baeldung.takes;

import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.http.FtCli;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkWrap;
import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;

public final class TakesApp {



//    public static void main(final String... args) throws Exception {
//        new FtBasic(new TkFork(new FkRegex("/", "hello, world... this is coming from Takes!")), 6060).start(Exit.NEVER);
//    }

        public static void main(final String... args) throws Exception {
            new FtBasic(new TakesIndex(), 6060).start(Exit.NEVER);
        }



}