package com.baeldung.features;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class JavaElevenFeaturesUnitTest {

    @Test
    void givenMultilineString_whenExtractingNonBlankStrippedLines_thenLinesAreReturned() {
        String multilineString = "Baeldung helps \n \n developers \n explore Java.";
        List<String> lines = multilineString.lines()
          .filter(line -> !line.isBlank())
          .map(String::strip)
          .collect(Collectors.toList());
        assertThat(lines).containsExactly("Baeldung helps", "developers", "explore Java.");
    }

    @Test
    void givenTemporaryFile_whenReadingStringContent_thenContentIsReturned(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.writeString(Files.createTempFile(tempDir, "demo", ".txt"), "Sample text");
        String fileContent = Files.readString(filePath);
        assertThat(fileContent).isEqualTo("Sample text");
    }

    @Test
    void givenSampleList_whenConvertingToArray_thenItemsRemainUnchanged() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String[] sampleArray = sampleList.toArray(String[]::new);
        assertThat(sampleArray).containsExactly("Java", "Kotlin");
    }

    @Test
    void givenSampleList_whenConvertingToUppercaseString_thenUppercaseIsReturned() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String resultString = sampleList.stream()
          .map((@Nonnull var x) -> x.toUpperCase())
          .collect(Collectors.joining(", "));
        assertThat(resultString).isEqualTo("JAVA, KOTLIN");
    }

    @Test
    void givenSampleList_whenExtractingNonBlankValues_thenOnlyNonBlanksAreReturned() {
        List<String> sampleList = Arrays.asList("Java", "\n \n", "Kotlin", " ");
        List<String> withoutBlanks = sampleList.stream()
          .filter(Predicate.not(String::isBlank))
          .collect(Collectors.toList());
        assertThat(withoutBlanks).containsExactly("Java", "Kotlin");
    }

}
