package com.baeldung.commons.lang3.test;

import java.io.File;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class SystemsUtilsUnitTest {

    @Test
    public void givenSystemUtilsClass_whenCalledgetJavaHome_thenCorrect() {
        assertThat(SystemUtils.getJavaHome()).isEqualTo(new File("/home/travis"));
    }

    @Test
    public void givenSystemUtilsClass_whenCalledgetUserHome_thenCorrect() {
        assertThat(SystemUtils.getUserHome()).isEqualTo(new File("/usr/lib/jvm/java-8-oracle/jre"));
    }

    @Test
    public void givenSystemUtilsClass_whenCalledisJavaVersionAtLeast_thenCorrect() {
        assertThat(SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_RECENT)).isTrue();
    }
}
