package com.baeldung.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link CheckedUncheckedExceptions}.
 */
public class CheckedUncheckedExceptionsUnitTest {

    @Test
    public void whenFileNotExist_thenThrowException() {
        assertThrows(FileNotFoundException.class, () -> {
            CheckedUncheckedExceptions.checkedExceptionWithThrows();
        });
    }

    @Test
    public void whenTryCatchExcetpion_thenSuccess() {
        try {
            CheckedUncheckedExceptions.checkedExceptionWithTryCatch();
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void whenDivideByZero_thenThrowException() {
        assertThrows(ArithmeticException.class, () -> {
            CheckedUncheckedExceptions.divideByZero();
        });
    }

    @Test
    public void whenInvalidFile_thenThrowException() {

        assertThrows(IncorrectFileNameException.class, () -> {
            CheckedUncheckedExceptions.checkFile("wrongFileName.txt");
        });
    }

    @Test
    public void whenNullOrEmptyFile_thenThrowException() {
        assertThrows(NullOrEmptyException.class, () -> {
            CheckedUncheckedExceptions.checkFile(null);
        });
        assertThrows(NullOrEmptyException.class, () -> {
            CheckedUncheckedExceptions.checkFile("");
        });
    }
}
