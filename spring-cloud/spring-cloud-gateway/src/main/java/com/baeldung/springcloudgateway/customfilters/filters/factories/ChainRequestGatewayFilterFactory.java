package com.baeldung.springcloudgateway.customfilters.filters.factories;

import java.util.Arrays;
import java.util.List;
import java.util.Locale.LanguageRange;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class ChainRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<ChainRequestGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(ChainRequestGatewayFilterFactory.class);

    private final WebClient client;

    public ChainRequestGatewayFilterFactory(WebClient client) {
        super(Config.class);
        this.client = client;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("languageServiceEndpoint", "defaultLanguage");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return client.get()
                .uri(config.getLanguageServiceEndpoint())
                .exchange()
                .flatMap(response -> {
                    return (response.statusCode()
                        .is2xxSuccessful()) ? response.bodyToMono(String.class) : Mono.just(config.getDefaultLanguage());
                })
                .map(LanguageRange::parse)
                .map(range -> {
                    exchange.getRequest()
                        .mutate()
                        .headers(h -> h.setAcceptLanguage(range));

                    String allOutgoingRequestLanguages = exchange.getRequest()
                        .getHeaders()
                        .getAcceptLanguage()
                        .stream()
                        .map(r -> r.getRange())
                        .collect(Collectors.joining(","));

                    logger.info("Chain Request output - Request contains Accept-Language header: " + allOutgoingRequestLanguages);

                    return exchange;
                })
                .flatMap(chain::filter);

        };
    }

    public static class Config {
        private String languageServiceEndpoint;
        private String defaultLanguage;

        public Config() {
        }

        public String getLanguageServiceEndpoint() {
            return languageServiceEndpoint;
        }

        public void setLanguageServiceEndpoint(String languageServiceEndpoint) {
            this.languageServiceEndpoint = languageServiceEndpoint;
        }

        public String getDefaultLanguage() {
            return defaultLanguage;
        }

        public void setDefaultLanguage(String defaultLanguage) {
            this.defaultLanguage = defaultLanguage;
        }
    }
}
