package com.baeldung.algorithms.stringpermutation;

import java.util.Collection;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.iterators.PermutationIterator;

public class StringPermutationsApache {

    public Collection<List<Character>> eagerPermutationWithRepetitions(final String string) {
        final List<Character> characters = ArrayHelper.toCharacterList(string);
        return CollectionUtils.permutations(characters);
    }

    public PermutationIterator<Character> lazyPermutationWithoutRepetitions(final String string) {
        final List<Character> characters = ArrayHelper.toCharacterList(string);
        return new PermutationIterator<>(characters);
    }

}
