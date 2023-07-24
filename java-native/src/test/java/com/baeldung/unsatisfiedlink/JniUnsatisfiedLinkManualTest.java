package com.baeldung.unsatisfiedlink;

import static com.baeldung.unsatisfiedlink.JniUnsatisfiedLink.LIB_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JniUnsatisfiedLinkManualTest {

    static String osLibPrefix = "";
    static String osLibSuffix = "";

    @BeforeAll
    static void setup() {
        String osName = System.getProperty("os.name")
            .toLowerCase();

        if (osName.contains("win")) {
            osLibSuffix = ".dll";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            osLibPrefix = "lib";
            osLibSuffix = ".so";
            if (osName.contains("mac")) {
                osLibPrefix = "lib";
                osLibSuffix = ".dylib";
            }
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + osName);
        }
    }

    @Test
    public void whenCorrectLibName_thenLibLoaded() {
        assertDoesNotThrow(() -> {
            System.loadLibrary(LIB_NAME);
            new JniUnsatisfiedLink().test();
        });
    }

    @Test
    public void whenIncorrectLibName_thenLibNotFound() {
        String libName = osLibPrefix + LIB_NAME + osLibSuffix;

        Error error = assertThrows(UnsatisfiedLinkError.class, () -> System.loadLibrary(libName));

        assertEquals(String.format("no %s in java.library.path", libName), error.getMessage());
    }

    @Test
    public void whenLoadLibraryContainsPathSeparator_thenErrorThrown() {
        String libName = "/" + LIB_NAME;

        Error error = assertThrows(UnsatisfiedLinkError.class, () -> System.loadLibrary(libName));

        assertEquals(String.format("Directory separator should not appear in library name: %s", libName), error.getMessage());
    }

    @Test
    public void whenLoadWithoutAbsolutePath_thenErrorThrown() {
        String libName = LIB_NAME;

        Error error = assertThrows(UnsatisfiedLinkError.class, () -> System.load(libName));

        assertEquals(String.format("Expecting an absolute path of the library: %s", libName), error.getMessage());
    }

    @Test
    public void whenUnlinkedMethod_thenErrorThrown() {
        System.loadLibrary(LIB_NAME);

        Error error = assertThrows(UnsatisfiedLinkError.class, () -> new JniUnsatisfiedLink().nonexistentDllMethod());

        assertTrue(error.getMessage()
            .contains("JniUnsatisfiedLink.nonexistentDllMethod"));
    }

    @Test
    public void whenIncompatibleArchitecture_thenErrorThrown() {
        Error error = assertThrows(UnsatisfiedLinkError.class, () -> System.loadLibrary(LIB_NAME + "32"));

        assertTrue(error.getMessage()
            .contains("wrong ELF class: ELFCLASS32"));
    }

    @Test
    public void whenCorruptedFile_thenErrorThrown() {
        String libPath = System.getProperty("java.library.path");
        assertNotNull(libPath);

        String dummyLib = LIB_NAME + "-dummy";
        assertTrue(new File(libPath, osLibPrefix + dummyLib + osLibSuffix).isFile());

        Error error = assertThrows(UnsatisfiedLinkError.class, () -> System.loadLibrary(dummyLib));

        assertTrue(error.getMessage()
            .contains("file too short"));
    }
}
