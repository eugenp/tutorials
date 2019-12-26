package com.baeldung.algorithms.caesarcipher;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CaesarCipherUnitTest {
    private static final String SENTENCE = "he told me i could never teach a llama to drive";
    private static final String SENTENCE_SHIFTED_THREE = "kh wrog ph l frxog qhyhu whdfk d oodpd wr gulyh";
    private static final String SENTENCE_SHIFTED_TEN = "ro dyvn wo s myevn xofob dokmr k vvkwk dy nbsfo";

    private CaesarCipher algorithm = new CaesarCipher();

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
    void givenSentenceShiftedTenAndShiftTen_whenDecipher_thenOriginalSentenceWithOverflow() {
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

    @Test
    void givenSentenceShiftedThree_whenBreakCipher_thenOriginalSentence() {
        int offset = algorithm.breakCipher(SENTENCE_SHIFTED_THREE);

        assertThat(offset)
          .isEqualTo(3);

        assertThat(algorithm.decipher(SENTENCE_SHIFTED_THREE, offset))
          .isEqualTo(SENTENCE);
    }

    @Test
    void givenSentenceShiftedTen_whenBreakCipher_thenOriginalSentence() {
        int offset = algorithm.breakCipher(SENTENCE_SHIFTED_TEN);

        assertThat(offset)
          .isEqualTo(10);

        assertThat(algorithm.decipher(SENTENCE_SHIFTED_TEN, offset))
          .isEqualTo(SENTENCE);
    }
}