package com.baeldung.boot.properties.multidocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.boot.properties.DemoApplication;

@SpringBootTest(classes = { DemoApplication.class }, webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("multidocument-integration")
public class IntegrationMultidocumentFilesIntegrationTest {

    @Value("${bael.property}")
    private String baelCustomProperty;
    
    @Value("${bael.root-level-property}")
    private String baelRootProperty;

    @Test
    public void givenProductionProfileActive_whenApplicationStarts_thenDefaultPropertiesUser() {
        assertThat(baelCustomProperty).isEqualTo("integrationValue");
        assertThat(baelRootProperty).isEqualTo("defaultRootLevelValue");
    }
}
