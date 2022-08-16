package com.baeldung.algorithms.stringpermutation;

import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.List;

public class StringPermutationsGuava {

    public Collection<List<Character>> permutationWithRepetitions(final String string) {
        final List<Character> characters = ArrayHelper.toCharacterList(string);
        return Collections2.permutations(characters);
    }
public Collection<List<Character>> permutationWithoutRepetitions(final String string) {
    final List<Character> characters = ArrayHelper.toCharacterList(string);
    return Collections2.orderedPermutations(characters);
}

}
