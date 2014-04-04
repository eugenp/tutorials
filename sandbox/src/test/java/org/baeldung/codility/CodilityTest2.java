package org.baeldung.codility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class CodilityTest2 {

    // tests

    @Test
    public final void whenSolutionIsCalculated1_thenCorrect() {
        final int solution = solution(955);
        Assert.assertEquals(4, solution);
    }

    @Test
    public final void whenSolutionIsCalculated2_thenCorrect() {
        final int solution = solution(102);
        Assert.assertEquals(-1, solution);
    }

    @Test
    public final void whenSolutionIsCalculated3_thenCorrect() {
        final int solution = solution(2);
        Assert.assertEquals(-1, solution);
    }

    @Test
    public final void whenSolutionIsCalculated4_thenCorrect() {
        final int solution = solution2("codilitycodilityco");
        Assert.assertEquals(8, solution);
    }

    //

    public final int solution(final int decimal) {
        final String binaryString = Integer.toBinaryString(decimal);
        int lastPeriod = -1;
        for (int period = 1; period < (binaryString.length() / 2 + 1); period++) {
            final Matcher m = Pattern.compile("(\\S{" + period + ",})(?=.*?\\1)").matcher(binaryString);
            final boolean found = m.find();
            if (found && m.groupCount() > 0) {
                lastPeriod = period;
            }
            if (!found) {
                break;
            }
        }

        return lastPeriod;
    }

    public final int solution2(final String binaryString) {
        int lastPeriod = -1;
        for (int period = 1; period < (binaryString.length() / 2 + 1); period++) {
            final Matcher m = Pattern.compile("(\\S{" + period + ",})(?=.*?\\1)").matcher(binaryString);
            final boolean found = m.find();
            if (found && m.groupCount() > 0) {
                lastPeriod = period;
            }
            if (!found) {
                break;
            }
        }

        return lastPeriod;
    }

}
