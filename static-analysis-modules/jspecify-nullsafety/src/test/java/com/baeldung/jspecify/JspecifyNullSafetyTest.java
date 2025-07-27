package com.baeldung.jspecify;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

public class JspecifyNullSafetyTest {

    @Test
    void givenKnownUserId_whenFindNickname_thenReturnsOptionalWithValue() {
        Optional<String> nickname = findNickname("user123");

        assertTrue(nickname.isPresent());
        assertEquals("CoolUser", nickname.get());
    }

    @Test
    void givenUnknownUserId_whenFindNickname_thenReturnsEmptyOptional() {
        Optional<String> nickname = findNickname("unknownUser");

        assertTrue(nickname.isEmpty());
    }

    @Test
    void givenNonNullArgument_whenValidate_thenDoesNotThrowException() {
        String result = processNickname("CoolUser");
        assertEquals("Processed: CoolUser", result);
    }

    @Test
    void givenNullArgument_whenValidate_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> processNickname(null));
    }

    @Test
    void givenUnknownUserId_whenFindNicknameOrNull_thenReturnsNull() {
        String nickname = findNicknameOrNull("unknownUser");
        assertNull(nickname);
    }

    @Test
    void givenNullableMethodResult_whenWrappedInOptional_thenHandledSafely() {
        String nickname = findNicknameOrNull("unknownUser");
        Optional<String> safeNickname = Optional.ofNullable(nickname);

        assertTrue(safeNickname.isEmpty());
    }

    private Optional<String> findNickname(String userId) {
        if ("user123".equals(userId)) {
            return Optional.of("CoolUser");
        } else {
            return Optional.empty();
        }
    }

    @Nullable
    private String findNicknameOrNull(String userId) {
        if ("user123".equals(userId)) {
            return "CoolUser";
        } else {
            return null;
        }
    }

    private String processNickname(String nickname) {
        Objects.requireNonNull(nickname, "Nickname must not be null");
        return "Processed: " + nickname;
    }
}
