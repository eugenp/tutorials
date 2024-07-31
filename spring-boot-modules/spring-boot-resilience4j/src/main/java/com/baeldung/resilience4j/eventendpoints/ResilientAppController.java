package com.baeldung.resilience4j.eventendpoints;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ResilientAppController {

  private final ExternalAPICaller externalAPICaller;

  @Autowired
  public ResilientAppController(ExternalAPICaller externalApi) {
    this.externalAPICaller = externalApi;
  }

  @GetMapping("/circuit-breaker")
  @CircuitBreaker(name = "externalService")
  public String circuitBreakerApi() {
    return externalAPICaller.callApi();
  }

  @GetMapping("/retry")
  @Retry(name = "externalService", fallbackMethod = "fallbackAfterRetry")
  public String retryApi() {
    return externalAPICaller.callApi();
  }

  @GetMapping("/bulkhead")
  @Bulkhead(name = "externalService")
  public String bulkheadApi() {
    return externalAPICaller.callApi();
  }

  @GetMapping("/rate-limiter")
  @RateLimiter(name = "externalService")
  public String rateLimitApi() {
    return externalAPICaller.callApi();
  }

  @GetMapping("/time-limiter")
  @TimeLimiter(name = "externalService")
  public CompletableFuture<String> timeLimiterApi() {
    return CompletableFuture.supplyAsync(externalAPICaller::callApiWithDelay);
  }

  public String fallbackAfterRetry(Exception ex) {
    return "all retries have exhausted";
  }
}
