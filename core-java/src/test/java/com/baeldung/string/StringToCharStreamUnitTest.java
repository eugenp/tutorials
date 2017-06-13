package com.baeldung.string;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class StringToCharStreamUnitTest {


    @Test
    public void givenTestString_whenChars_thenReturnIntStream() {
        assertThat(StringToCharStream.getIntStreamFromChars("test"), instanceOf(IntStream.class));
    }

    @Test
    public void givenTestString_whenCodePoints_thenReturnIntStream() {
        assertThat(StringToCharStream.getIntStreamFromCodePoints("test"), instanceOf(IntStream.class));
    }

    @Test
    public void givenIntStream_whenMapToObj_thenReturnCharacterStream() {
        Stream<Character> characterStream
          = StringToCharStream.mapIntStreamToCharStream(StringToCharStream.getIntStreamFromChars("test"));
        Stream<Character> characterStream1
          = StringToCharStream.mapIntStreamToCharStream(StringToCharStream.getIntStreamFromCodePoints("test"));
        assertNotNull("IntStream returned by chars() did not map to Stream<Character>", characterStream);
        assertNotNull("IntStream returned by codePoints() did not map to Stream<Character>", characterStream1);
    }

    @Test
    public void givenIntStream_whenMapToObj_thenReturnStringStream() {
        Stream<String> stringStream
          = StringToCharStream.mapIntStreamToStringStream(StringToCharStream.getIntStreamFromCodePoints("test"));
        assertNotNull(stringStream);
    }

}
