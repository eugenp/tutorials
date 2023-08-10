package com.baeldung.avaje.intro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;

@InjectTest
class ExampleInjectTest {

  @Mock Shield shield;

  @Inject Knight knight;

  @Test
  void givenMockedShield_whenGetShield_thenShieldShouldHaveMockedValue() {

    Mockito.when(shield.defense()).thenReturn(0);
    assertNotNull(knight);
    assertNotNull(knight.sword());
    assertNotNull(knight.shield());
    assertEquals(0, knight.shield().defense());
  }
}
