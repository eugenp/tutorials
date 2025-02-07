package com.baeldung;

import jdk.jfr.Event;
import jdk.jfr.Name;

@Name("com.baeldung.DatabaseQueryEvent")
public class DatabaseQueryEvent extends Event {

    private final String query;
    private final long executionTime;

    public DatabaseQueryEvent(String query, long executionTime) {
        this.query = query;
        this.executionTime = executionTime;
    }

    public String getQuery() {
        return query;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
