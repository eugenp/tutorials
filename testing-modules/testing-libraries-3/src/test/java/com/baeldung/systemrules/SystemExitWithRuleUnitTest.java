package com.baeldung.systemrules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class SystemExitWithRuleUnitTest {

    @Rule
    public final ExpectedSystemExit exitRule = ExpectedSystemExit.none();

    @Test
    public void givenSystemExitRule_whenAppCallsSystemExit_thenExitRuleWorkssAsExpected() {
        exitRule.expectSystemExitWithStatus(1);
        exit();
    }

    private void exit() {
        System.exit(1);
    }

}
