package com.baeldung.passwordhashing;

import com.baeldung.passwordhashing.SHA512Hasher;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;

import static org.junit.Assert.*;

/**
 * Created by PhysicsSam on 06-Sep-18.
 */
public class SHA512HasherUnitTest {

  private SHA512Hasher hasher;
  private SecureRandom secureRandom;

  @Before
  public void setUp() throws Exception {
    hasher = new SHA512Hasher();
    secureRandom = new SecureRandom();
  }

  @Test
  public void givenSamePasswordAndSalt_whenHashed_checkResultingHashesAreEqual() throws Exception {

    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);

    String hash1 = hasher.get_SHA_512_SecurePassword("password", salt);
    String hash2 = hasher.get_SHA_512_SecurePassword("password", salt);

    assertEquals(hash1, hash2);

  }

  @Test
  public void givenSamePasswordAndDifferentSalt_whenHashed_checkResultingHashesNotEqual() throws Exception {

    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);
    String hash1 = hasher.get_SHA_512_SecurePassword("password", salt);
    //generate a second salt
    byte[] secondSalt = new byte[16];
    String hash2 = hasher.get_SHA_512_SecurePassword("password", secondSalt);

    assertNotEquals(hash1, hash2);

  }

  @Test
  public void givenPredefinedHash_whenCorrectAttemptGiven_checkAuthenticationSucceeds() throws Exception {
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);

    String originalHash = hasher.get_SHA_512_SecurePassword("password123", salt);

    assertTrue(hasher.authenticate(originalHash, "password123", salt));
  }

  @Test
  public void givenPredefinedHash_whenIncorrectAttemptGiven_checkAuthenticationFails() throws Exception {
    byte[] salt = new byte[16];
    secureRandom.nextBytes(salt);

    String originalHash = hasher.get_SHA_512_SecurePassword("password123", salt);

    assertFalse(hasher.authenticate(originalHash, "password124", salt));
  }
}