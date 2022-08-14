package com.baeldung.algorithms.stringpermutation;

import java.util.List;
import java.util.stream.Collectors;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.PermutationGenerator.TreatDuplicatesAs;

public class StringPermutationsCombinatoricsLib {

    public List<List<Character>> permutationWithoutRepetitions(final String string) {
        List<Character> chars = ArrayHelper.toCharacterList(string);
        return Generator.permutation(chars)
            .simple()
            .stream()
            .collect(Collectors.toList());
    }

    public List<List<Character>> permutationWithRepetitions(final String string) {
        List<Character> chars = ArrayHelper.toCharacterList(string);
        return Generator.permutation(chars)
            .simple(TreatDuplicatesAs.IDENTICAL)
            .stream()
            .collect(Collectors.toList());
    }

}
