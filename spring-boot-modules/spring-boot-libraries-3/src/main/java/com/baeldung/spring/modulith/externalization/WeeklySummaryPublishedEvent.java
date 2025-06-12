package com.baeldung.spring.modulith.externalization;

import org.springframework.modulith.events.Externalized;

@Externalized
record WeeklySummaryPublishedEvent(String handle, String heading) {
}