package com.baeldung.algorithms.vigenere;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VigenereCipherUnitTest {

    @Test
    void encodeBaeldung() {
        VigenereCipher cipher = new VigenereCipher();
        String output = cipher.encode("BAELDUNG", "HELLO");

        Assertions.assertEquals("JFQXSCSS", output);
    }

    @Test
    void encodeBaeldungMixedCharacters() {
        VigenereCipher cipher = new VigenereCipher("JQFVHPWORZSLNMKYCGBUXIEDTA");
        String output = cipher.encode("BAELDUNG", "HELLO");

        Assertions.assertEquals("DERDPTZV", output);
    }

    @Test
    void encodeArticleTitle() {
        VigenereCipher cipher = new VigenereCipher();
        String output = cipher.encode("VIGENERE CIPHER IN JAVA", "BAELDUNG");

        Assertions.assertEquals("XJLQRZFL EJUTIM WU LBAM", output);
    }

    @Test
    void encodeArticleTitleMoreCharacters() {
        VigenereCipher cipher = new VigenereCipher("ABCDEFGHIJKLMNOPQRSTUVWXYZ ");
        String output = cipher.encode("VIGENERE CIPHER IN JAVA", "BAELDUNG");

        Assertions.assertEquals("XJLQRZELBDNALZEGKOEVEPO", output);
    }

    @Test
    void decodeBaeldung() {
        VigenereCipher cipher = new VigenereCipher();
        String output = cipher.decode("JFQXSCSS", "HELLO");

        Assertions.assertEquals("BAELDUNG", output);
    }

    @Test
    void decodeBaeldungMixedCharacters() {
        VigenereCipher cipher = new VigenereCipher("JQFVHPWORZSLNMKYCGBUXIEDTA");
        String output = cipher.decode("DERDPTZV", "HELLO");

        Assertions.assertEquals("BAELDUNG", output);
    }

    @Test
    void decodeArticleTitleMoreCharacters() {
        VigenereCipher cipher = new VigenereCipher("ABCDEFGHIJKLMNOPQRSTUVWXYZ ");
        String output = cipher.decode("XJLQRZELBDNALZEGKOEVEPO", "BAELDUNG");

        Assertions.assertEquals("VIGENERE CIPHER IN JAVA", output);
    }

    @Test
    void encodeDecodeBaeldung() {
        VigenereCipher cipher = new VigenereCipher();

        String input = "BAELDUNG";
        String key = "HELLO";

        String encoded = cipher.encode(input, key);
        String decoded = cipher.decode(encoded, key);

        Assertions.assertEquals(input, decoded);
    }
}
