package com.baeldung.algorithms.stringpermutation;

import com.google.common.collect.Collections2;
import java.util.List;
import java.util.stream.Collectors;

public class StringPermutationsGuava {

    public List<String> permutationWithRepetitions(final String string) {
        final List<Character> characters = Helper.toCharacterList(string);
        return Collections2.permutations(characters).stream()
            .map(Helper::toString)
            .collect(Collectors.toList());
    }
    public List<String> permutationWithoutRepetitions(final String string) {
        final List<Character> characters = Helper.toCharacterList(string);
        return Collections2.orderedPermutations(characters).stream()
            .map(Helper::toString)
            .collect(Collectors.toList());
    }

}
