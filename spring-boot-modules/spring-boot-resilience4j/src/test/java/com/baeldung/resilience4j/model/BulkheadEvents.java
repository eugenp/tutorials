package com.baeldung.resilience4j.model;

import java.util.List;

public class BulkheadEvents {

    private List<BulkheadEvent> bulkheadEvents;

    public List<BulkheadEvent> getBulkheadEvents() {
        return bulkheadEvents;
    }

    public void setBulkheadEvents(List<BulkheadEvent> bulkheadEvents) {
        this.bulkheadEvents = bulkheadEvents;
    }
}
