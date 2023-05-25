package com.baeldung.webclient.filter;

import java.io.PrintStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

public class WebClientFilters {

    private static final Logger LOG = LoggerFactory.getLogger(WebClientFilters.class);

    public static ExchangeFilterFunction demoFilter() {
        ExchangeFilterFunction filterFunction = (clientRequest, nextFilter) -> {
            LOG.info("WebClient fitler executed");
            return nextFilter.exchange(clientRequest);
        };
        return filterFunction;
    }

    public static ExchangeFilterFunction countingFilter(AtomicInteger getCounter) {
        ExchangeFilterFunction countingFilter = (clientRequest, nextFilter) -> {
            HttpMethod httpMethod = clientRequest.method();
            if (httpMethod == HttpMethod.GET) {
                getCounter.incrementAndGet();
            }
            return nextFilter.exchange(clientRequest);
        };
        return countingFilter;
    }

    public static ExchangeFilterFunction urlModifyingFilter(String version) {
        ExchangeFilterFunction urlModifyingFilter = (clientRequest, nextFilter) -> {
            String oldUrl = clientRequest.url()
                .toString();
            URI newUrl = URI.create(oldUrl + "/" + version);
            ClientRequest filteredRequest = ClientRequest.from(clientRequest)
                .url(newUrl)
                .build();
            return nextFilter.exchange(filteredRequest);
        };
        return urlModifyingFilter;
    }

    public static ExchangeFilterFunction loggingFilter(PrintStream printStream) {
        ExchangeFilterFunction loggingFilter = (clientRequest, nextFilter) -> {
            printStream.print("Sending request " + clientRequest.method() + " " + clientRequest.url());
            return nextFilter.exchange(clientRequest);
        };
        return loggingFilter;
    }

}
