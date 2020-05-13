package com.baeldung.ratelimiting.bucket4japp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baeldung.ratelimiting.bucket4japp.service.PricingPlanService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final String HEADER_API_KEY = "X-api-key";
    private static final String HEADER_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    private static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";

    @Autowired
    private PricingPlanService pricingPlanService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String apiKey = request.getHeader(HEADER_API_KEY);

        if (apiKey == null || apiKey.isEmpty()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Header: " + HEADER_API_KEY);
            return false;
        }

        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);

        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {

            response.addHeader(HEADER_LIMIT_REMAINING, String.valueOf(probe.getRemainingTokens()));
            return true;

        } else {

            long waitForRefill = probe.getNanosToWaitForRefill() % 1_000_000_000;

            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exhausted your API Request Quota"); // 429
            response.addHeader(HEADER_RETRY_AFTER, String.valueOf(waitForRefill));

            return false;
        }
    }
}