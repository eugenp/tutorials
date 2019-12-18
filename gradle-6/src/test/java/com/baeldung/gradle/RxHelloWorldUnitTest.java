package com.baeldung.gradle;

import static com.baeldung.gradle.RxHelloWorld.hello;

import org.junit.jupiter.api.Test;

/** Unit test for {@link RxHelloWorld}. */
final class RxHelloWorldUnitTest {

  @Test
  void it_emits_hello_world_values() {
    hello().test().assertValues("hello", "world").assertComplete();
  }
}
