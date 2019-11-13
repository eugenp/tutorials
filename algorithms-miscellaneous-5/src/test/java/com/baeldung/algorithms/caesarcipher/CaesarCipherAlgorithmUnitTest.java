package com.baeldung.algorithms.caesarcipher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaesarCipherAlgorithmUnitTest {
    private static final String SENTENCE = "he told me i could never teach a lama to drive";

    private CaesarCipherAlgorithm algorithm = new CaesarCipherAlgorithm();

    @Test
    void givenSentenceAndShiftThree_whenCipher_thenCipheredMessageWithoutOverflow() {
        String cipheredSentence = algorithm.cipher(SENTENCE, 3);

        assertThat(cipheredSentence)
          .isEqualTo("kh wrog ph l frxog qhyhu whdfk d odpd wr gulyh");
    }

    @Test
    void givenSentenceAndShiftTen_whenCipher_thenCipheredMessageWithOverflow() {
        String cipheredSentence = algorithm.cipher(SENTENCE, 10);

        assertThat(cipheredSentence)
          .isEqualTo("ro dyvn wo s myevn xofob dokmr k vkwk dy nbsfo");
    }
}