package com.baeldung.springsecuredsockets.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This is required to enable springSecurityFilterChain.
 *
 * Remember that Spring Security utilizes filters to intercept and manage requests
 * according to the specified authorization and authentication rules
 */

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}