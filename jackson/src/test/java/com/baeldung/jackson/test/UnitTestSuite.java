package com.baeldung.jackson.test;

import com.baeldung.jackson.sandbox.JacksonPrettyPrintUnitTest;
import com.baeldung.jackson.sandbox.SandboxUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
     JacksonDeserializationUnitTest.class
    ,JacksonDeserializationUnitTest.class
    ,JacksonPrettyPrintUnitTest.class
    ,SandboxUnitTest.class
}) // @formatter:on
public class UnitTestSuite {
}