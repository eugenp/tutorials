package com.baeldung.formparamdoc;

import java.util.List;

public record SubscriptionResponse(long id, String email, String frequency, List<String> topics, boolean marketingAccepted) {
}
