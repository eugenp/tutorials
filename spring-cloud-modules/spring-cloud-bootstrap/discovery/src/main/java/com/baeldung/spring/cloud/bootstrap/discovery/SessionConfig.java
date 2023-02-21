package com.baeldung.spring.cloud.bootstrap.discovery;

import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@EnableSpringHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
}
