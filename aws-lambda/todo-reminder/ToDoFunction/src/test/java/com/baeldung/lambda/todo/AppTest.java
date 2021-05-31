package com.baeldung.lambda.todo;

import com.amazonaws.services.lambda.runtime.Context;
import com.baeldung.lambda.todo.config.Config;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.org.webcompere.lightweightconfig.ConfigLoader;
import uk.org.webcompere.systemstubs.rules.EnvironmentVariablesRule;
import uk.org.webcompere.systemstubs.stream.input.LinesAltStream;
import uk.org.webcompere.systemstubs.stream.output.NoopStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Context mockContext;

    @Rule
    public EnvironmentVariablesRule environmentVariablesRule = new EnvironmentVariablesRule();

    private InputStream fakeInputStream = new LinesAltStream();
    private OutputStream fakeOutputStream = new NoopStream();

    @Test
    public void whenTheEnvironmentVariableIsSet_thenItIsLogged() throws Exception {
        environmentVariablesRule.set("ENV_NAME", "unitTest");
        new App().handleRequest(fakeInputStream, fakeOutputStream, mockContext);

        verify(mockContext.getLogger())
          .log("Environment: unitTest\n");
    }

    @Test
    public void givenEnvironmentVariableIsNotSet_thenUseDefault() {
        String setting = Optional.ofNullable(System.getenv("SETTING"))
          .orElse("default");

        assertThat(setting).isEqualTo("default");
    }

    @Test
    public void givenConfiguration_canLoadIntoPojo() {
        environmentVariablesRule.set("ENV_NAME", "unitTest");
        Config config = ConfigLoader.loadYmlConfigFromResource("configuration.yml", Config.class);
        assertThat(config.getEnvironmentName()).isEqualTo("unitTest");
    }
}