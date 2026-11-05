package com.baeldung.springai.anthropic.ratelimit;

import java.time.Duration;

public record MetadataResponse(String model, Long requestsLimit, Long requestsRemaining, Duration tokensReset, Long tokensLimit, Long tokensRemaining,
                               Integer promptTokens, Integer completionTokens, String content) {

}
