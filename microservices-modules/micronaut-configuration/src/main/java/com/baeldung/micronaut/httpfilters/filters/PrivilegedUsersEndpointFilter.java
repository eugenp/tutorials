package com.baeldung.micronaut.httpfilters.filters;

import static com.baeldung.micronaut.httpfilters.utils.CustomHttpHeaders.PRIVILEGED_USER_HEADER_KEY;

import org.reactivestreams.Publisher;

import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.rxjava3.core.Flowable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Filter(patterns = { "**/*1" })
public class PrivilegedUsersEndpointFilter implements HttpServerFilter, Ordered {

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        log.info("Privileged user was filtered");

        return Flowable.fromPublisher(chain.proceed(request))
            .doOnNext(response -> response.getHeaders()
                .add(PRIVILEGED_USER_HEADER_KEY, "true"));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
