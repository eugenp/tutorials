package com.baeldung.quarkus;

import io.quarkus.test.junit.SubstrateTest;

@SubstrateTest
public class NativeHelloResourceIT extends HelloResourceUnitTest {

    // Execute the same tests but in native mode.
}