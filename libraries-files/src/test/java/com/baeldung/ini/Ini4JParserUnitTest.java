package com.baeldung.ini;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import static com.baeldung.ini.Ini4JParser.readIniFileValue;
import static com.baeldung.ini.Ini4JParser.parseIniFile;
import static org.assertj.core.api.Assertions.assertThat;

class Ini4JParserUnitTest {

    private static final File TEST_FILE = Paths.get("src", "test", "resources", "sample.ini").toFile();

    @Test
    void givenIniFileThenCanParseWithIni4j() throws Exception {
        Map<String, Map<String, String>> result =
          parseIniFile(TEST_FILE);

        assertThat(result.get("fonts"))
          .containsEntry("letter", "bold")
          .containsEntry("text-size", "28");
    }

    @Test
    void givenIniFileThenCanReadKeyFromIt() throws Exception {
        assertThat(readIniFileValue(TEST_FILE, "fonts", "letter"))
                .isEqualTo("bold");
    }
}