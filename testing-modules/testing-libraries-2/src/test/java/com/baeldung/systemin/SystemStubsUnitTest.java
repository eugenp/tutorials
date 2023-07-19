package com.baeldung.systemin;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemInRule;

import static com.baeldung.systemin.Application.NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemStubsUnitTest {

    @Rule
    public SystemInRule systemInRule = new SystemInRule("Baeldung");

    @Test
    public void givenName_whenReadWithSystemStubs_thenReturnCorrectResult() {
        assertThat(Application.readName()).isEqualTo(NAME.concat("Baeldung"));
    }
}
