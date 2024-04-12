package com.baeldung.crunch;

import java.util.Set;

import org.apache.crunch.FilterFn;

import com.google.common.collect.ImmutableSet;

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
