package com.baeldung.jarfile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.base.Ascii;
import java.io.File;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JarFilePathResolverUnitTest {
    @Spy
    JarFilePathResolver jarFilePathResolver;

    @Test
    void givenClassObjectWhenCallingByGetProtectionDomainShouldGetExpectedPath() throws Exception {
        String jarPath = jarFilePathResolver.byGetProtectionDomain(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
    }

    @Test
    void givenClassObjectWhenCallingByGetResourceShouldGetExpectedPath() {
        String jarPath = jarFilePathResolver.byGetResource(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
    }

    @Test
    void givenClassObjectWhenNoSecurityExceptionRaisedShouldGetExpectedPath() throws URISyntaxException {
        String jarPath = jarFilePathResolver.getJarFilePath(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
        verify(jarFilePathResolver, times(1)).byGetProtectionDomain(Ascii.class);
        verify(jarFilePathResolver, never()).byGetResource(Ascii.class);
    }

    @Test
    void givenClassObjectWhenSecurityExceptionRaisedShouldGetExpectedPath() throws URISyntaxException {
        when(jarFilePathResolver.byGetProtectionDomain(Ascii.class)).thenThrow(new SecurityException("not allowed"));
        String jarPath = jarFilePathResolver.getJarFilePath(Ascii.class);
        assertThat(jarPath).endsWith(".jar").contains("guava");
        assertThat(new File(jarPath)).exists();
        verify(jarFilePathResolver, times(1)).byGetProtectionDomain(Ascii.class);
        verify(jarFilePathResolver, times(1)).byGetResource(Ascii.class);
    }

}
