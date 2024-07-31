package com.baeldung.algorithms.stringpermutation;

import java.util.List;
import java.util.stream.Collectors;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.PermutationGenerator.TreatDuplicatesAs;

public class StringPermutationsCombinatoricsLib {

public List<String> permutationWithoutRepetitions(final String string) {
    List<Character> chars = Helper.toCharacterList(string);
    return Generator.permutation(chars)
        .simple()
        .stream()
        .map(Helper::toString)
        .collect(Collectors.toList());
}

    public List<String> permutationWithRepetitions(final String string) {
        List<Character> chars = Helper.toCharacterList(string);
        return Generator.permutation(chars)
            .simple(TreatDuplicatesAs.IDENTICAL)
            .stream()
            .map(Helper::toString)
            .collect(Collectors.toList());
    }

}
