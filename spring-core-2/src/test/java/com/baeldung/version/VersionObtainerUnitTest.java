package com.baeldung.version;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VersionObtainerUnitTest {

    public VersionObtainer version = new VersionObtainer();
    
    @Test
    public void testGetSpringVersion() {
        String res = version.getSpringVersion();
        assertThat(res).isNotEmpty();
    }

    @Test
    public void testGetJdkVersion() {
        String res = version.getJdkVersion();
        assertThat(res).isNotEmpty();
    }

    @Test
    public void testGetJavaVersion() {
        String res = version.getJavaVersion();
        assertThat(res).isNotEmpty();
    }
}
