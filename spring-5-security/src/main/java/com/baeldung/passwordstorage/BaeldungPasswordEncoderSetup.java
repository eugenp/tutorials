package com.baeldung.passwordstorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BaeldungPasswordEncoderSetup {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // set up the list of supported encoders and their prefixes
        String encodingId = "rot13";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new Rot13PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        // get an instance of the DelegatingPasswordEncoder, set up to use our instance as default encoder
        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);

        // configure our instance as default encoder for actual matching
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encodingId));

        return delegatingPasswordEncoder;
    }
}
