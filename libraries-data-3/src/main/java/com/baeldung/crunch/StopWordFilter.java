package com.baeldung.crunch;

import com.google.common.collect.ImmutableSet;
import org.apache.crunch.FilterFn;

import java.util.Set;

/**
 * A filter that removes known stop words.
 */
public class StopWordFilter extends FilterFn<String> {

    // English stop words, borrowed from Lucene.
    private static final Set<String> STOP_WORDS = ImmutableSet
        .copyOf(new String[] { "a", "and", "are", "as", "at", "be", "but", "by",
                "for", "if", "in", "into", "is", "it", "no", "not", "of", "on",
                "or", "s", "such", "t", "that", "the", "their", "then", "there",
                "these", "they", "this", "to", "was", "will", "with" });

    @Override
    public boolean accept(String word) {
        return !STOP_WORDS.contains(word);
    }
}
