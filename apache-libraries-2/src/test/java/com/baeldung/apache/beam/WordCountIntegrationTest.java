package com.baeldung.apache.beam;

import com.baeldung.apache.beam.WordCount;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WordCountIntegrationTest {
    
    @Test
    public void givenInputFile_whenWordCountRuns_thenJobFinishWithoutError() {
        boolean jobDone = WordCount.wordCount("src/test/resources/wordcount.txt", "target/output");
        assertTrue(jobDone);
    }

}
