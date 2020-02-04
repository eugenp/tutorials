package com.baeldung.multiline;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MultiLineStringsUnitTest {

    private Yaml yaml;

    @Before
    public void setup() {
        yaml = new Yaml();
    }

    @Test
    public void whenLiteral_ThenLineBreaksArePresent() {
        String key = parseYamlKey("literal.yaml");
        assertEquals("Line1\nLine2\nLine3", key);
    }

    @Test
    public void whenLiteral_ThenEndingBreaksAreReducedToOne() {
        String key = parseYamlKey("literal2.yaml");
        assertEquals("\n\nLine1\n\nLine2\n\nLine3\n", key);
    }

    @Test
    public void whenFolded_ThenLineBreaksAreReplaced() {
        String key = parseYamlKey("folded.yaml");
        assertEquals("Line1 Line2 Line3", key);
    }

    @Test
    public void whenFolded_ThenEmptyLinesAreReducedToOne() {
        String key = parseYamlKey("folded2.yaml");
        assertEquals("Line1\nLine2\n\nLine3\n", key);
    }

    @Test
    public void whenLiteralKeep_ThenLastEmptyLinesArePresent() {
        String key = parseYamlKey("literal_keep.yaml");
        assertEquals("Line1\nLine2\nLine3\n\n", key);
    }

    @Test
    public void whenLiteralStrip_ThenLastEmptyLinesAreRemoved() {
        String key = parseYamlKey("literal_strip.yaml");
        assertEquals("Line1\nLine2\nLine3", key);
    }

    @Test
    public void whenFoldedKeep_ThenLastEmptyLinesArePresent() {
        String key = parseYamlKey("folded_keep.yaml");
        assertEquals("Line1 Line2 Line3\n\n\n", key);
    }

    @Test
    public void whenFoldedStrip_ThenLastEmptyLinesAreRemoved() {
        String key = parseYamlKey("folded_strip.yaml");
        assertEquals("Line1 Line2 Line3", key);
    }

    @Test
    public void whenDoubleQuotes_ThenExplicitBreaksArePreserved() {
        String key = parseYamlKey("plain_double_quotes.yaml");
        assertEquals("Line1\nLine2\nLine3", key);
    }

    @Test
    public void whenSingleQuotes_ThenExplicitBreaksAreIgnored() {
        String key = parseYamlKey("plain_single_quotes.yaml");
        assertEquals("Line1\\nLine2\nLine3", key);
    }

    private String parseYamlKey(String fileName) {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("multi-line" + File.separator + fileName);
        Map<String, String> parsed = yaml.load(inputStream);
        return parsed.get("key");
    }

}
