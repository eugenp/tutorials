package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.ConnectionProvider;
import com.github.davidmoten.rx.jdbc.ConnectionProviderFromUrl;

public class Connector {

    public static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    public static final String DB_USER = "";
    public static final String DB_PASSWORD = "";

    public static final ConnectionProvider connectionProvider = new ConnectionProviderFromUrl(DB_CONNECTION, DB_USER, DB_PASSWORD);
}
