package com.baeldung.algorithms.caesarcipher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaesarCipherAlgorithmUnitTest {
    private static final String SENTENCE = "he told me i could never teach a lama to drive";
    private static final String SENTENCE_SHIFTED_THREE = "kh wrog ph l frxog qhyhu whdfk d odpd wr gulyh";
    private static final String SENTENCE_SHIFTED_TEN = "ro dyvn wo s myevn xofob dokmr k vkwk dy nbsfo";

    private CaesarCipherAlgorithm algorithm = new CaesarCipherAlgorithm();

    @Test
    void givenSentenceAndShiftThree_whenCipher_thenCipheredMessageWithoutOverflow() {
        String cipheredSentence = algorithm.cipher(SENTENCE, 3);

        assertThat(cipheredSentence)
          .isEqualTo(SENTENCE_SHIFTED_THREE);
    }

    @Test
    void givenSentenceAndShiftTen_whenCipher_thenCipheredMessageWithOverflow() {
        String cipheredSentence = algorithm.cipher(SENTENCE, 10);

        assertThat(cipheredSentence)
          .isEqualTo(SENTENCE_SHIFTED_TEN);
    }

    @Test
    void givenSentenceAndShiftThirtySix_whenCipher_thenCipheredLikeTenMessageWithOverflow() {
        String cipheredSentence = algorithm.cipher(SENTENCE, 36);

        assertThat(cipheredSentence)
          .isEqualTo(SENTENCE_SHIFTED_TEN);
    }

    @Test
    void givenSentenceShiftedThreeAndShiftThree_whenDecipher_thenOriginalSentenceWithoutOverflow() {
        String decipheredSentence = algorithm.decipher(SENTENCE_SHIFTED_THREE, 3);

        assertThat(decipheredSentence)
          .isEqualTo(SENTENCE);
    }

    @Test
    void givenSentenceShiftedTenAndShitTen_whenDecipher_thenOriginalSentenceWithOverflow() {
        String decipheredSentence = algorithm.decipher(SENTENCE_SHIFTED_TEN, 10);

        assertThat(decipheredSentence)
          .isEqualTo(SENTENCE);
    }

    @Test
    void givenSentenceShiftedTenAndShiftThirtySix_whenDecipher_thenOriginalSentenceWithOverflow() {
        String decipheredSentence = algorithm.decipher(SENTENCE_SHIFTED_TEN, 36);

        assertThat(decipheredSentence)
          .isEqualTo(SENTENCE);
    }
}