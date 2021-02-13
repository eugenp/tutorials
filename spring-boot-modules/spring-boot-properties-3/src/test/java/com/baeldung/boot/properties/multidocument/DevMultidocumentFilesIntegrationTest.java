package com.baeldung.boot.properties.multidocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.boot.properties.DemoApplication;

@SpringBootTest(classes = { DemoApplication.class }, webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("multidocument-dev")
public class DevMultidocumentFilesIntegrationTest {

    @Value("${bael.property}")
    private String baelCustomProperty;
    
    @Value("${bael.root-level-property}")
    private String baelRootProperty;

    @Test
    public void givenDefaultProfileActive_whenApplicationStarts_thenDefaultPropertiesUser() {
        assertThat(baelCustomProperty).isEqualTo("devValue");
        assertThat(baelRootProperty).isEqualTo("defaultRootLevelValue");
    }
}
