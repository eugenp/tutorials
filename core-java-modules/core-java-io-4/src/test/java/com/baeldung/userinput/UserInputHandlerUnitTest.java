package com.baeldung.userinput;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInputHandlerUnitTest {

    @Test
    public void givenDataInSystemIn_whenCallingReadUserInputMethod_thenHaveUserInputData() {
        String[] inputLines = new String[]{
            "The first line.",
            "The second line.",
            "The last line.",
            "bye",
            "anything after 'bye' will be ignored"
        };
        String[] expectedLines = Arrays.copyOf(inputLines, inputLines.length - 2);
        List<String> expected = Arrays.stream(expectedLines).collect(Collectors.toList());
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(String.join("\n", inputLines).getBytes()));
            List<String> actual = UserInputHandler.readUserInput();
            assertThat(actual).isEqualTo(expected);
        } finally {
            System.setIn(stdin);
        }
    }
}