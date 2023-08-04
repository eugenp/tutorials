package com.baeldung.avaje.intro;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import io.avaje.inject.BeanScope;

class AvajeUnitTest {

  @Test
  void givenBeanScope_whenScopeGetsKnight_thenKnightShouldHaveDependencies() {

    final var scope = BeanScope.builder().build();
    final var knight = scope.get(Knight.class);

    assertNotNull(knight);
    assertNotNull(knight.sword());
    assertNotNull(knight.shield());
    assertEquals(25, knight.shield().defense());
  }
}
