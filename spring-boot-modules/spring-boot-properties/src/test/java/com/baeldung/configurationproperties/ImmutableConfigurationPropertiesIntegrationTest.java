package com.baeldung.configurationproperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImmutableConfigPropertiesApp.class)
@TestPropertySource("classpath:configprops-test.properties")
public class ImmutableConfigurationPropertiesIntegrationTest {

    @Autowired
    private ImmutableCredentials immutableCredentials;

    @Test
    public void whenConstructorBindingUsed_thenPropertiesCorrectlyBound() {
        assertThat(immutableCredentials.getAuthMethod()).isEqualTo("SHA1");
        assertThat(immutableCredentials.getUsername()).isEqualTo("john");
        assertThat(immutableCredentials.getPassword()).isEqualTo("password");
    }
}
