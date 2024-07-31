package com.baeldung.ini;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import static com.baeldung.ini.Ini4JParser.parseIniFile;
import static org.assertj.core.api.Assertions.assertThat;

class SerializeIntoPojoUnitTest {

    private static final File TEST_FILE = Paths.get("src", "test", "resources", "sample.ini").toFile();

    @Test
    void givenAnIniFileThenCanLoadAsPojo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Map<String, String>> iniKeys = parseIniFile(TEST_FILE);

        MyConfiguration config = objectMapper.convertValue(iniKeys, MyConfiguration.class);

        assertThat(config.getFonts().getLetter()).isEqualTo("bold");
        assertThat(config.getFonts().getTextSize()).isEqualTo(28);
        assertThat(config.getBackground().getColor()).isEqualTo("white");
        assertThat(config.getRequestResult().getRequestCode()).isEqualTo(1);
        assertThat(config.getResponseResult().getResultCode()).isZero();
    }
}
