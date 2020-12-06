package com.baeldung.features;

import org.junit.jupiter.api.Test;

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
        String multilineString = "My name \n \n is \n Daniel.";
        List<String> lines = multilineString.lines()
                .filter(line -> !line.isBlank())
                .map(String::strip)
                .collect(Collectors.toList());
        assertThat(lines).containsExactly("My name", "is", "Daniel.");
    }

    @Test
    void givenTemporaryFile_whenReadingStringContent_thenContentIsReturned() throws IOException {
        Path filePath = Files.writeString(Files.createTempFile("demo", ".txt"), "Sample text");
        String fileContent = Files.readString(filePath);
        assertThat(fileContent).isEqualTo("Sample text");
    }

    @Test
    void givenSampleList_whenConvertingToArray_thenItemsRemainUnchanged() {
        List<String> sampleList = Arrays.asList("Daniel", "Sanja");
        String[] sampleArray = sampleList.toArray(String[]::new);
        assertThat(sampleArray).containsExactly("Daniel", "Sanja");
    }

    @Test
    void givenSampleList_whenConvertingToUppercaseString_thenUppercaseIsReturned() {
        List<String> sampleList = Arrays.asList("Daniel", "Sanja");
        String resultString = sampleList.stream()
                .map((@Nonnull var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));
        assertThat(resultString).isEqualTo("DANIEL, SANJA");
    }

    @Test
    void givenSampleList_whenExractingEvenNumbers_thenOnlyEvenNumbersAreReturned() {
        List<Integer> allNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Predicate<Integer> isOdd = i -> i % 2 == 0;
        List<Integer> evenNumbers = allNumbers.stream()
                .filter(Predicate.not(isOdd))
                .collect(Collectors.toList());
        assertThat(evenNumbers).containsExactly(1, 3, 5);
    }

}
