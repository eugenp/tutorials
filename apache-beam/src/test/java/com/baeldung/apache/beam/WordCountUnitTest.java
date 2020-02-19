package com.baeldung.apache.beam;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class WordCountUnitTest {
    
    @Test
    @Ignore
    public void givenInputFile_whenWordCountRuns_thenJobFinishWithoutError() {
        boolean jobDone = WordCount.wordCount("src/test/resources/wordcount.txt", "target/output");
        assertTrue(jobDone);
    }

}
