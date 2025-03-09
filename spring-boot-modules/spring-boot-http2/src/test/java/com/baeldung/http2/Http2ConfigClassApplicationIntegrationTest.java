package com.baeldung.http2;

import org.springframework.test.context.ActiveProfiles;

// Integration test based on enabling HTTP/2 using the configuration class Http2Config
@ActiveProfiles("config-class")
class Http2ConfigClassApplicationIntegrationTest extends Http2ApplicationIntegrationTest {
}