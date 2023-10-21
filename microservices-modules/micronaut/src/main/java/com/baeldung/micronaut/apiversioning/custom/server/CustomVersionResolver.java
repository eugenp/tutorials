package com.baeldung.micronaut.apiversioning.custom.server;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.web.router.version.resolution.RequestVersionResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
@Requires(property = "my.router.versioning.enabled", value = "true")
public class CustomVersionResolver implements RequestVersionResolver {

    @Inject
    @Value("${micronaut.router.versioning.default-version}")
    private String defaultVersion;

    @Override
    public Optional<String> resolve(HttpRequest<?> request) {
        var apiKey = Optional.ofNullable(request.getHeaders().get("api-key"));

        if (apiKey.isPresent() && !apiKey.get().isEmpty()) {
            return Optional.of(Integer.parseInt(apiKey.get())  % 2 == 0 ? "2" : "1");
        }

        return Optional.of(defaultVersion);
    }

}
