package com.baeldung.stringfilenamevalidaiton;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.stream.Stream;

import static com.baeldung.stringfilenamevalidaiton.StringFilenameValidationUtils.validateStringFilenameUsingContains;
import static com.baeldung.stringfilenamevalidaiton.StringFilenameValidationUtils.validateStringFilenameUsingIO;
import static com.baeldung.stringfilenamevalidaiton.StringFilenameValidationUtils.validateStringFilenameUsingNIO2;
import static com.baeldung.stringfilenamevalidaiton.StringFilenameValidationUtils.validateStringFilenameUsingRegex;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringFilenameValidationUnitTest {

    private static final String CORRECT_FILENAME_PATTERN = "baeldung.txt";

    @ParameterizedTest
    @MethodSource("correctAlphanumericFilenamesProvider")
    public void givenCorrectAlphanumericRandomFilenameString_whenValidateUsingIO_thenReturnTrue(String filename) throws IOException {
        assertThat(validateStringFilenameUsingIO(filename)).isTrue();
        assertThat(validateStringFilenameUsingNIO2(filename)).isTrue();
        assertThat(validateStringFilenameUsingContains(filename)).isTrue();
        assertThat(validateStringFilenameUsingRegex(filename)).isTrue();
    }

    @Test
    public void givenTooLongFileNameString_whenValidate_thenIOAndCustomFailsNIO2Succeed() {
        String filename = RandomStringUtils.randomAlphabetic(500);
        assertThatThrownBy(() -> validateStringFilenameUsingIO(filename))
            .isInstanceOf(IOException.class)
            .hasMessageContaining("File name too long");
        assertThat(validateStringFilenameUsingNIO2(filename)).isTrue();
        assertThat(validateStringFilenameUsingContains(filename)).isFalse();
        assertThat(validateStringFilenameUsingRegex(filename)).isFalse();
    }

    @ParameterizedTest
    @NullSource
    public void givenNullString_whenValidate_thenFails(String filename) {
        assertThatThrownBy(() -> validateStringFilenameUsingIO(filename))
            .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> validateStringFilenameUsingNIO2(filename))
            .isInstanceOf(NullPointerException.class);
        assertThat(validateStringFilenameUsingContains(filename)).isFalse();
        assertThat(validateStringFilenameUsingRegex(filename)).isFalse();
    }

    @ParameterizedTest
    @EmptySource
    public void givenEmptyString_whenValidate_thenIOAndCustomFailsNIO2Succeed(String filename) {
        assertThatThrownBy(() -> validateStringFilenameUsingIO(filename))
            .isInstanceOf(IOException.class);
        assertThat(validateStringFilenameUsingNIO2(filename)).isTrue();
        assertThat(validateStringFilenameUsingContains(filename)).isFalse();
        assertThat(validateStringFilenameUsingRegex(filename)).isFalse();
    }

    @ParameterizedTest
    @EnabledOnOs({OS.LINUX, OS.MAC})
    @MethodSource("filenamesWithInvalidWindowsChars")
    public void givenFilenameStringWithInvalidWindowsCharAndIsUnix_whenValidateUsingIO_thenReturnTrue(String filename) throws IOException {
        assertThat(validateStringFilenameUsingIO(filename)).isTrue();
        assertThat(validateStringFilenameUsingNIO2(filename)).isTrue();
        assertThat(validateStringFilenameUsingContains(filename)).isTrue();
    }

    @ParameterizedTest
    @EnabledOnOs(OS.WINDOWS)
    @MethodSource("filenamesWithInvalidWindowsChars")
	public void givenFilenameStringWithInvalidWindowsCharAndIsWindows_whenValidateUsingIO_thenRaiseException(
			String filename) {
		if (!filename.contains(":")) {
			assertThatThrownBy(() -> validateStringFilenameUsingIO(filename)).isInstanceOf(IOException.class)
					.extracting(Throwable::getMessage, InstanceOfAssertFactories.STRING)
					.containsAnyOf("The system cannot find the path specifie",
							"The filename, directory name, or volume label syntax is incorrect");
			if (!filename.contains("\\")) {
				assertThatThrownBy(() -> validateStringFilenameUsingNIO2(filename))
						.isInstanceOf(InvalidPathException.class).hasMessageContaining("Illegal char");
			}
		}

		assertThat(validateStringFilenameUsingContains(filename)).isFalse();
	}

    @ParameterizedTest
    @EnabledOnOs({OS.LINUX, OS.MAC})
    @MethodSource("filenamesWithInvalidUnixChars")
    public void givenFilenameStringWithInvalidUnixCharAndIsUnix_whenValidate_thenRaiseException(String filename) {
        assertThatThrownBy(() -> validateStringFilenameUsingIO(filename))
            .isInstanceOf(IOException.class)
            .hasMessageContaining("Invalid file path");

        assertThatThrownBy(() -> validateStringFilenameUsingNIO2(filename))
            .isInstanceOf(InvalidPathException.class)
            .hasMessageContaining("character not allowed");

        assertThat(validateStringFilenameUsingContains(filename)).isFalse();
    }


    private static Stream<String> correctAlphanumericFilenamesProvider() {
        return Stream.generate(() -> RandomStringUtils.randomAlphanumeric(1, 10) + "." + RandomStringUtils.randomAlphabetic(3, 5)).limit(10);
    }

    private static Stream<String> filenamesWithInvalidWindowsChars() {
        return Arrays.stream(StringFilenameValidationUtils.INVALID_WINDOWS_SPECIFIC_CHARS)
            .map(character -> {
                int idx = RandomUtils.nextInt(0, CORRECT_FILENAME_PATTERN.length());
                return CORRECT_FILENAME_PATTERN.substring(0, idx) + character + CORRECT_FILENAME_PATTERN.substring(idx);
            });
    }

    private static Stream<String> filenamesWithInvalidUnixChars() {
        return Arrays.stream(StringFilenameValidationUtils.INVALID_UNIX_SPECIFIC_CHARS)
            .map(character -> {
                int idx = RandomUtils.nextInt(0, CORRECT_FILENAME_PATTERN.length());
                return CORRECT_FILENAME_PATTERN.substring(0, idx) + character + CORRECT_FILENAME_PATTERN.substring(idx);
            });
    }

}
