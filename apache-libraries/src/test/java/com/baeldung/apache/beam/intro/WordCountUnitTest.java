package com.baeldung.apache.beam.intro;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.baeldung.apache.beam.intro.WordCount;

public class WordCountUnitTest {
    
    @Test
    public void givenInputFile_whenWordCountRuns_thenJobFinishWithoutError() {
        boolean jobDone = WordCount.wordCount("src/test/resources/wordcount.txt", "target/output");
        assertTrue(jobDone);
    }

}
