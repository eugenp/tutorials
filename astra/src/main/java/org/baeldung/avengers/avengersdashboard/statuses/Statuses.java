package org.baeldung.avengers.avengersdashboard.statuses;

import java.util.Map;

public record Statuses(Map<String, Status> data) {}