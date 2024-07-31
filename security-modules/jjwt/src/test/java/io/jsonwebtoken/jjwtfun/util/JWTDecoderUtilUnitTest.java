package io.jsonwebtoken.jjwtfun.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JWTDecoderUtilUnitTest {

    private final static String SIMPLE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkJhZWxkdW5nIFVzZXIiLCJpYXQiOjE1MTYyMzkwMjJ9";
    private final static String SIGNED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkJhZWxkdW5nIFVzZXIiLCJpYXQiOjE1MTYyMzkwMjJ9.6h_QYBTbyKxfMq3TGiAhVI416rctV0c0SpzWxVm-0-Y";

    @Test
    void givenSimpleToken_whenDecoding_thenStringOfHeaderPayloadAreReturned() {
        assertThat(JWTDecoderUtil.decodeJWTToken(SIMPLE_TOKEN))
          .contains(SignatureAlgorithm.HS256.getValue());
    }

    @Test
    void givenSignedToken_whenDecodingWithInvalidSecret_thenIntegrityIsNotValidated() {
        assertThatThrownBy(() -> JWTDecoderUtil.
            isTokenValid(SIGNED_TOKEN, "BAD_SECRET"))
          .hasMessage("Could not verify JWT token integrity!");
    }

    @Test
    void givenSignedToken_whenDecodingWithValidSecret_thenIntegrityIsValidated() throws Exception {
        assertTrue(JWTDecoderUtil.isTokenValid(SIGNED_TOKEN, "randomSecretWithSome!!CharacterS!"));
    }
}
