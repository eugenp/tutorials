package com.baeldung.lombok.intro;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
@EqualsAndHashCode(of = {"authToken"})
public class LoginResult {

    private final @NonNull Instant loginTs;

    private final @NonNull String authToken;
    private final @NonNull Duration tokenValidity;

    private final @NonNull URL tokenRefreshUrl;

}
