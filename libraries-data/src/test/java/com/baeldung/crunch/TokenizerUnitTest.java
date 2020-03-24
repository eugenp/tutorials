package com.baeldung.crunch;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.apache.crunch.Emitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenizerUnitTest {
    @Mock
    private Emitter<String> emitter;

    @Test
    public void givenTokenizer_whenLineProcessed_thenOnlyExpectedWordsEmitted() {
        Tokenizer splitter = new Tokenizer();

        splitter.process("  hello  world ", emitter);

        verify(emitter).emit("hello");
        verify(emitter).emit("world");
        verifyNoMoreInteractions(emitter);
    }
}
