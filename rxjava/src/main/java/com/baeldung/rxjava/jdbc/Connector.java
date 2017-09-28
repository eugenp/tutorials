package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;

class Connector {

    static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    static final String DB_USER = "";
    static final String DB_PASSWORD = "";

    static final ConnectionProvider connectionProvider = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
}
