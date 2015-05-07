package org.baeldung.jackson.test;

import org.baeldung.jackson.sandbox.JacksonPrettyPrintUnitTest;
import org.baeldung.jackson.sandbox.SandboxTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({// @formatter:off
    JacksonCollectionDeserializationUnitTest.class
    ,JacksonSerializationEnumsUnitTest.class
    ,JacksonDeserializationUnitTest.class
    ,JacksonDeserializationUnitTest.class
    ,JacksonPrettyPrintUnitTest.class
    ,JacksonSerializationIgnoreUnitTest.class
    ,JacksonSerializationUnitTest.class
    ,SandboxTest.class
    ,JacksonFieldUnitTest.class
}) // @formatter:on
public class UnitTestSuite {
}