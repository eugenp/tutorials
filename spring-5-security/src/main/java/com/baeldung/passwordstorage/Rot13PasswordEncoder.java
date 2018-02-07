package com.baeldung.passwordstorage;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DISCLAIMER: Never ever use this in any production environment!
 * <p>
 * Does only work for characters.
 */
public class Rot13PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        StringBuffer result = new StringBuffer(rawPassword.length());
        rawPassword
          .chars()
          .forEach(charCode -> {
              if (charCode >= 65 && charCode <= 77 || charCode >= 97 && charCode <= 109) {
                  result.append(Character.toChars(charCode + 13));
              } else if (charCode >= 78 && charCode <= 90 || charCode >= 110 && charCode <= 133) {
                  result.append(Character.toChars(charCode - 13));
              }
          });

        return result.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
