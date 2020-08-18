package com.baeldung.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
@QuarkusTestResource(H2DatabaseTestResource.class)
class NativeLibraryResourceIT extends LibraryHttpEndpointIntegrationTest {
}
