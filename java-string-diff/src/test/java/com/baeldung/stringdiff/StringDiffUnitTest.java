package com.baeldung.stringdiff;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.Test;

public class StringDiffUnitTest {

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    @Test
    public void testStringDiff() {
        String text1 = "ABCDELMN";
        String text2 = "ABCFGLMN";

        // Diff with Diff-Match-Patch
        System.out.println(diffMatchPatch.diffMain(text1, text2, false));
        System.out.println(diffMatchPatch.diffMain(text2, text1, false));

        // Diff with StringUtils
        System.out.println(StringUtils.difference(text1, text2));
        System.out.println(StringUtils.difference(text2, text1));
    }

    @Test
    public void testPerformance() {
        // Create input
        List<String> inputs = randomizeInputs(10000);

        // Google diff match patch
        LocalDateTime begin = LocalDateTime.now();
        for (int i = 0; i < inputs.size() - 1; i++) {
            diffMatchPatch.diffMain(inputs.get(i), inputs.get(i + 1), false);
        }
        System.out.println("Diff-Match-Patch Duration: " + Duration.between(begin, LocalDateTime.now())
            .toMillis());

        // Apache commons
        begin = LocalDateTime.now();
        for (int i = 0; i < inputs.size() - 1; i++) {
            StringUtils.difference(inputs.get(i), inputs.get(i + 1));
        }
        System.out.println("StringUtils Duration: " + Duration.between(begin, LocalDateTime.now())
            .toMillis());
    }

    /**
     * Creates a list of a given size, containing 30 character long strings,
     * each starting with a static prefix of 10 characters and followed by
     * a random 20 character suffix
     *
     * @return a {@link List} of randomised strings
     */
    private List<String> randomizeInputs(int size) {
        String staticPart = "ABCDEF1234";
        List<String> inputs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            inputs.add(staticPart + RandomStringUtils.randomAlphabetic(20));
        }
        return inputs;
    }

}
