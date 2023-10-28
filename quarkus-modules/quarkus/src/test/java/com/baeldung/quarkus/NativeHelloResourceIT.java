package com.baeldung.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class NativeHelloResourceIT extends HelloResourceUnitTest {

    // Execute the same tests but in native mode.
}