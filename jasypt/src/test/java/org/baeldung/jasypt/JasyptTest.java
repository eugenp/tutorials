package org.baeldung.jasypt;


import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

public class JasyptTest {

    @Test
    public void givenTextPassword_whenDecrypt_shouldCompareToEncrypted() {
        //given
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        String password = "secret-pass";
        textEncryptor.setPasswordCharArray(password.toCharArray());

        //when
        String myEncryptedText = textEncryptor.encrypt("secret-pass");
        assertNotSame("secret-pass", myEncryptedText); //myEncryptedText can be save in db

        //then
        String plainText = textEncryptor.decrypt(myEncryptedText);
        assertEquals(plainText, "secret-pass");
    }
}
