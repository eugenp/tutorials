package com.baeldung.unrecoverablekeyexception;

import java.security.UnrecoverableKeyException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

public class KeyManagerInitializerUnitTest {

  @Test
  public void givenPasswordIsCorrect_whenInitializingTheKeyManager_thenNoExceptionIsThrown() {
    // Given.
    String privateKeyPassword = "privateKeyPassword";

    // When.
    ThrowingCallable initializeKeyManager = () -> KeyManagerInitializer.initializeKeyManager(privateKeyPassword);

    // Then.
    Assertions.assertThatCode(initializeKeyManager).doesNotThrowAnyException();;
  }

  @Test
  public void givenPasswordIsWrong_whenInitializingTheKeyManager_thenUnrecoverableKeyExceptionExceptionIsThrown() {
    // Given.
    String privateKeyPassword = "wrongPassword";

    // When.
    ThrowingCallable initializeKeyManager = () -> KeyManagerInitializer.initializeKeyManager(privateKeyPassword);

    // Then.
    Assertions.assertThatThrownBy(initializeKeyManager).isInstanceOf(UnrecoverableKeyException.class);
  }

  @Test
  public void givenMultipleKeysWithDifferentPasswordsInKeystore_whenInitializingTheKeyManager_thenUnrecoverableKeyExceptionIsThrown() {
    // Given.
    String firstPrivateKeyPassword = "abc123";

    // When.
    ThrowingCallable initializeKeyManager = () -> KeyManagerInitializer.initializeKeyManager(firstPrivateKeyPassword, "multi_entry_keystore.jks");

    // Then.
    Assertions.assertThatThrownBy(initializeKeyManager).isInstanceOf(UnrecoverableKeyException.class);
  }
}