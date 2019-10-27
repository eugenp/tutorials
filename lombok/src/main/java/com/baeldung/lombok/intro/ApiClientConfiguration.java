package com.baeldung.lombok.intro;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
@Getter
public class ApiClientConfiguration {

    private String host;
    private int port;
    private boolean useHttps;

    private long connectTimeout;
    private long readTimeout;

    private String username;
    private String password;

}
