package com.baeldung.stringfilenamevalidaiton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class StringFilenameValidationUtils {

    public static final Character[] INVALID_WINDOWS_SPECIFIC_CHARS = {'"', '*', '<', '>', '?', '|'};
    public static final Character[] INVALID_UNIX_SPECIFIC_CHARS = {'\000'};

    public static final String REGEX_PATTERN = "^[A-za-z0-9.]{1,255}$";

    private StringFilenameValidationUtils() {
    }

    public static boolean validateStringFilenameUsingIO(String filename) throws IOException {
        File file = new File(filename);
        boolean created = false;
        try {
            created = file.createNewFile();
            return created;
        } finally {
            if (created) {
                file.delete();
            }
        }
    }

    public static boolean validateStringFilenameUsingNIO2(String filename) {
        Paths.get(filename);
        return true;
    }

    public static boolean validateStringFilenameUsingContains(String filename) {
        if (filename == null || filename.isEmpty() || filename.length() > 255) {
            return false;
        }
        return Arrays.stream(getInvalidCharsByOS())
            .noneMatch(ch -> filename.contains(ch.toString()));
    }

    public static boolean validateStringFilenameUsingRegex(String filename) {
        if (filename == null) {
            return false;
        }
        return filename.matches(REGEX_PATTERN);
    }

    private static Character[] getInvalidCharsByOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return INVALID_WINDOWS_SPECIFIC_CHARS;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return INVALID_UNIX_SPECIFIC_CHARS;
        } else {
            return new Character[]{};
        }
    }
}
