package com.baeldung.springmodulith.events.externalization;

import org.springframework.modulith.events.Externalized;

@Externalized
record WeeklySummaryPublishedEvent(String handle, String heading) {
}