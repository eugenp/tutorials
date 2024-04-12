package com.gateway.gateway.ratelimiting;

import com.gateway.security.SecurityUtils;

import io.github.jhipster.config.JHipsterProperties;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul filter for limiting the number of HTTP calls per client.
 */
public class RateLimitingFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(RateLimitingFilter.class);

    private static final String TIME_PERIOD = "hour";

    private long rateLimit = 100000L;

    private final RateLimitingRepository rateLimitingRepository;

    public RateLimitingFilter(RateLimitingRepository rateLimitingRepository, JHipsterProperties jHipsterProperties) {
        this.rateLimitingRepository = rateLimitingRepository;
        this.rateLimit = jHipsterProperties.getGateway().getRateLimiting().getLimit();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        // specific APIs can be filtered out using
        // if (RequestContext.getCurrentContext().getRequest().getRequestURI().startsWith("/api")) { ... }
        return true;
    }

    @Override
    public Object run() {
        String id = getId(RequestContext.getCurrentContext().getRequest());
        Date date = getPeriod();

        // check current rate limit
        // default limit per user is 100,000 API calls per hour
        Long count = rateLimitingRepository.getCounter(id, TIME_PERIOD, date);
        log.debug("Rate limiting for user {} at {} - {}",  id, date, count);
        if (count > rateLimit) {
            apiLimitExceeded();
        } else {
            // count calls per hour
            rateLimitingRepository.incrementCounter(id, TIME_PERIOD, date);
        }
        return null;
    }

    private void apiLimitExceeded() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody("API rate limit exceeded");
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * The ID that will identify the limit: the user login or the user IP address.
     */
    private String getId(HttpServletRequest httpServletRequest) {
        String login = SecurityUtils.getCurrentUserLogin();
        if (login != null) {
            return login;
        } else {
          return httpServletRequest.getRemoteAddr();
        }
    }

    /**
     * The period for which the rate is calculated.
     */
    private Date getPeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.MILLISECOND);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MINUTE);
        return calendar.getTime();
    }
}
