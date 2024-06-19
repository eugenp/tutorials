package com.baeldung.privatekeyreader;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Collections;

public class PrivateKeyReader {

    public static void main(String[] args) throws Exception {

        try (InputStream is = PrivateKeyReader.class.getClassLoader().getResourceAsStream("mykeystore.jks")) {
            // Load the keystore
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] passwordCharArr = "mypassword".toCharArray();
            keystore.load(is, passwordCharArr);

            // Iterate through the aliases
            for (String alias : Collections.list(keystore.aliases())) {
                if (keystore.isKeyEntry(alias)) {
                    KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keystore.getEntry(alias, new KeyStore.PasswordProtection(passwordCharArr));
                    PrivateKey privateKey = pkEntry.getPrivateKey();
                    System.out.println("Alias: " + alias);
                    System.out.println("-----BEGIN PRIVATE KEY-----");
                    System.out.println(Base64.getMimeEncoder(64, "\n".getBytes())
                        .encodeToString(privateKey.getEncoded()));
                    System.out.println("-----END PRIVATE KEY-----");
                }
            }
        }
    }

}
