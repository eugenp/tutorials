package com.baeldung.kdf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Key Derivation Function (KDF) API introduced in Java 25.
 */
class KdfApiUnitTest {

    private byte[] ikm;
    private byte[] salt;
    private byte[] info;

    @BeforeEach
    void setUp() {
        // Initialize sample input key material, salt, and info context
        ikm = new byte[32];
        salt = "standard-salt".getBytes();
        info = "encryption-context".getBytes();
    }

    @Test
    void givenHkdfAlgorithm_whenDeriveKeyForAes_thenReturnsValidSecretKey()
      throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        // given
        KDF kdf = KDF.getInstance("HKDF-SHA256");
        HKDFParameterSpec params = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .thenExpand(info, 32);

        // when
        SecretKey aesKey = kdf.deriveKey("AES", params);

        // then
        assertThat(aesKey).isNotNull();
        assertThat(aesKey.getAlgorithm()).isEqualTo("AES");
        assertThat(aesKey.getEncoded()).hasSize(32);
    }

    @Test
    void givenHkdfAlgorithm_whenDeriveData_thenReturnsRawBytes()
      throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        // given
        KDF kdf = KDF.getInstance("HKDF-SHA256");
        HKDFParameterSpec params = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .thenExpand(info, 64);

        // when
        byte[] derivedData = kdf.deriveData(params);

        // then
        assertThat(derivedData)
          .isNotNull()
          .hasSize(64);
    }

    @Test
    void givenExtractOnlyMode_whenDeriveKey_thenReturnsPseudorandomKey()
      throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        // given
        KDF kdf = KDF.getInstance("HKDF-SHA256");
        HKDFParameterSpec extractOnlyParams = HKDFParameterSpec.ofExtract()
          .addIKM(ikm)
          .addSalt(salt)
          .extractOnly();

        // when
        SecretKey prk = kdf.deriveKey("HKDF-SHA256", extractOnlyParams);

        // then
        assertThat(prk).isNotNull();
        assertThat(prk.getAlgorithm()).isEqualTo("HKDF-SHA256");
    }

    @Test
    void givenInvalidAlgorithm_whenGetInstance_thenThrowsNoSuchAlgorithmException() {
        // when & then
        assertThatThrownBy(() -> KDF.getInstance("INVALID-KDF"))
          .isInstanceOf(NoSuchAlgorithmException.class);
    }

    @Test
    void givenIncompatibleParameters_whenDeriveKey_thenThrowsException()
      throws NoSuchAlgorithmException {

        // given
        KDF kdf = KDF.getInstance("HKDF-SHA256");
        // Providing null or invalid spec

        // when & then
        assertThatThrownBy(() -> kdf.deriveKey("AES", null))
          .isInstanceOf(InvalidAlgorithmParameterException.class);
    }
}
