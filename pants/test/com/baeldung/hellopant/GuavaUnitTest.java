package com.baeldung.hellopant;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

public class GuavaUnitTest {

    @Test
    public void whenConvertListToStringAndSkipNull_thenConverted() {
        final List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        final String result = Joiner.on(",").skipNulls().join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenGettingTextFromAResourceFile_thenJoined() throws IOException {
        String world = Resources.toString(Resources.getResource(GuavaUnitTest.class, "word.txt"), Charsets.UTF_8).strip();
        String result = Joiner.on(" ").join("Hello", world);
        assertEquals(result, "Hello from Us");
    }
  
}
