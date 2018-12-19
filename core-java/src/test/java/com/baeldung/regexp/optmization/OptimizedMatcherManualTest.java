package com.baeldung.regexp.optmization;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class OptimizedMatcherManualTest {

    private String action;

    private List<String> items;
    
    private class TimeWrapper {
        private long time;
        private long mstimePreCompiled;
        private long mstimeNotPreCompiled;
    }

    @Before
    public void setup() {
        Random random = new Random();
        items = new ArrayList<String>();
        long average = 0;

        for (int i = 0; i < 100000; ++i) {
            StringBuilder s = new StringBuilder();
            int characters = random.nextInt(7) + 1;
            for (int k = 0; k < characters; ++ k) {
                char c = (char)(random.nextInt('Z' - 'A') + 'A');
                int rep = random.nextInt(95) + 5;
                for (int j = 0; j < rep; ++ j)
                    s.append(c);
                average += rep;
            }
            items.add(s.toString());
        }

        average /= 100000;
        System.out.println("generated data, average length: " + average);
    }


    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternA_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {
        TimeWrapper timeObj = new TimeWrapper();
        testPatterns("A*", timeObj);
        assertTrue(timeObj.mstimePreCompiled < timeObj.mstimeNotPreCompiled);
    }

    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternABC_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {
        TimeWrapper timeObj = new TimeWrapper();
        testPatterns("A*B*C*", timeObj);
        assertTrue(timeObj.mstimePreCompiled < timeObj.mstimeNotPreCompiled);
    }

    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternECWF_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {
        TimeWrapper timeObj = new TimeWrapper();
        testPatterns("E*C*W*F*", timeObj);
        assertTrue(timeObj.mstimePreCompiled < timeObj.mstimeNotPreCompiled);
    }

    private void testPatterns(String regex, TimeWrapper timeObj) {
        timeObj.time = System.nanoTime();
        int matched = 0;
        int unmatched = 0;

        for (String item : this.items) {
            if (item.matches(regex)) {
                ++matched;
            }
            else {
                ++unmatched;
            }
        }

        this.action = "uncompiled: regex=" + regex + " matched=" + matched + " unmatched=" + unmatched;

        timeObj.mstimeNotPreCompiled = (System.nanoTime() - timeObj.time) / 1000000;
        System.out.println(this.action + ": " + timeObj.mstimeNotPreCompiled + "ms");

        timeObj.time = System.nanoTime();

        Matcher matcher = Pattern.compile(regex).matcher("");
        matched = 0;
        unmatched = 0;

        for (String item : this.items) {
            if (matcher.reset(item).matches()) {
                ++matched;
            }
            else {
                ++unmatched;
            }
        }

        this.action = "compiled: regex=" + regex + " matched=" + matched + " unmatched=" + unmatched;

        timeObj.mstimePreCompiled = (System.nanoTime() - timeObj.time) / 1000000;
        System.out.println(this.action + ": " + timeObj.mstimePreCompiled + "ms");
    }
}
