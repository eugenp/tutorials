package com.baeldung.passwordhashing;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

/** A really simple SimplePBKDF2 Encryption example.
 *
 */
public class SimplePBKDF2Hasher {

  public static String hashSimple(String password, byte[] salt) throws Exception{
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = f.generateSecret(spec).getEncoded();
    return String.valueOf(hash);
  }
}
