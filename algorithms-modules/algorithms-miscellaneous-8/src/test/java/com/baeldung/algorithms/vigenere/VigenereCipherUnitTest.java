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
        String output = cipher.encode("VEGENERE CIPHER IN JAVA", "BAELDUNG");

        Assertions.assertEquals("XFLQRZFL EJUTIM WU LBAM", output);
    }

    @Test
    void encodeArticleTitleMoreCharacters() {
        VigenereCipher cipher = new VigenereCipher("ABCDEFGHIJKLMNOPQRSTUVWXYZ ");
        String output = cipher.encode("VEGENERE CIPHER IN JAVA", "BAELDUNG");

        Assertions.assertEquals("XFLQRZELBDNALZEGKOEVEPO", output);
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
        String output = cipher.decode("XFLQRZELBDNALZEGKOEVEPO", "BAELDUNG");

        Assertions.assertEquals("VEGENERE CIPHER IN JAVA", output);
    }
}
