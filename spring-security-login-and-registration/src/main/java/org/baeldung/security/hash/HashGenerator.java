package org.baeldung.security.hash;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {

    public String getHashedPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
