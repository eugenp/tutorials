package com.baeldung.springcloudgateway.customfilters.filters.factories;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ModifyRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<ModifyRequestGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(ModifyRequestGatewayFilterFactory.class);

    public ModifyRequestGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("defaultLocale");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (exchange.getRequest()
                .getHeaders()
                .getAcceptLanguage()
                .isEmpty()) {

                String queryParamLocale = exchange.getRequest()
                    .getQueryParams()
                    .getFirst("locale");

                Locale requestLocale = Optional.ofNullable(queryParamLocale)
                    .map(l -> Locale.forLanguageTag(l))
                    .orElse(config.getDefaultLocale());

                exchange.getRequest()
                    .mutate()
                    .headers(h -> h.setAcceptLanguageAsLocales(Collections.singletonList(requestLocale)))
                    .build();
            }

            String allOutgoingRequestLanguages = exchange.getRequest()
                .getHeaders()
                .getAcceptLanguage()
                .stream()
                .map(range -> range.getRange())
                .collect(Collectors.joining(","));

            logger.info("Modify Request output - Request contains Accept-Language header: " + allOutgoingRequestLanguages);
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private Locale defaultLocale;

        public Config() {
        }

        public Locale getDefaultLocale() {
            return defaultLocale;
        }

        public void setDefaultLocale(String defaultLocale) {
            this.defaultLocale = Locale.forLanguageTag(defaultLocale);
        };
    }
}
