package com.baeldung.crunch;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;

import com.google.common.base.Splitter;

/**
 * Splits a line of text, filtering known stop words.
 */
public class Tokenizer extends DoFn<String, String> {
    private static final Splitter SPLITTER = Splitter
        .onPattern("\\s+")
        .omitEmptyStrings();

    @Override
    public void process(String line,
        Emitter<String> emitter) {
        for (String word : SPLITTER.split(line)) {
            emitter.emit(word);
        }
    }
}
