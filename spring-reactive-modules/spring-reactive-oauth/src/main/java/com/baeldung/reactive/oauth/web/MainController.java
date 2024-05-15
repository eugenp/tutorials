package com.baeldung.reactive.oauth.web;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.baeldung.reactive.oauth.web.dto.Foo;

@RestController
public class MainController {
    
    @Autowired
    private WebClient webClient;
    
    @GetMapping("/")
    public Mono<String> index(@AuthenticationPrincipal Mono<OAuth2User> oauth2User) {
       return oauth2User
        .map(OAuth2User::getName)
        .map(name -> String.format("Hi, %s", name));
    }
    
    @GetMapping("/foos/{id}")
    public Mono<Foo> getFooResource(@RegisteredOAuth2AuthorizedClient("custom") OAuth2AuthorizedClient client, @PathVariable final long id){
        return webClient
            .get()
            .uri("http://localhost:8088/spring-security-oauth-resource/foos/{id}", id)
            .attributes(oauth2AuthorizedClient(client))
            .retrieve()
        .bodyToMono(Foo.class); 
    }
   
    @GetMapping("/about")
    public String getAboutPage() {
       return "WebFlux OAuth example";
    }
}
