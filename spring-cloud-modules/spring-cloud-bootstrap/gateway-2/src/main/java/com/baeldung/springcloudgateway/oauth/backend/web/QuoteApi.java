package com.baeldung.springcloudgateway.oauth.backend.web;

import javax.annotation.PostConstruct;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springcloudgateway.oauth.backend.domain.Quote;

import reactor.core.publisher.Mono;

@RestController
public class QuoteApi {
    private static final GrantedAuthority GOLD_CUSTOMER = new SimpleGrantedAuthority("gold");

    @GetMapping("/quotes/{symbol}")
    public Mono<Quote> getQuote(@PathVariable("symbol") String symbol, BearerTokenAuthentication auth ) {
        
        Quote q = new Quote();
        q.setSymbol(symbol);
        
        if ( auth.getAuthorities().contains(GOLD_CUSTOMER)) {
            q.setPrice(10.0);
        }
        else {
            q.setPrice(12.0);
        }
        return Mono.just(q);
    }
}
