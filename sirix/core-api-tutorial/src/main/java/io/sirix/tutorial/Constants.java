package io.sirix.tutorial;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {
    private Constants() {
    }

    static final String USER_HOME = System.getProperty("user.home");

    static final Path SIRIX_DATA_LOCATION = Paths.get(USER_HOME, "sirix-data");
}
