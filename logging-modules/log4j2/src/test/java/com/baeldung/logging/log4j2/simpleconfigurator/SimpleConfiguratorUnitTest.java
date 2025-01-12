/**
 * This class demonstrates how to use ConfigurationBuilderFactory directly,
 * as described in Section 3 of "Programmatic Configuration with Log4j 2"
 **/

package com.baeldung.logging.log4j2.simpleconfigurator;

import com.baeldung.logging.log4j2.Log4j2BaseUnitTest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SimpleConfiguratorUnitTest extends Log4j2BaseUnitTest {

    @Test
    public void givenDefaultLog4j2Environment_whenProgrammaticallyConfigured_thenLogsCorrectly() {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        AppenderComponentBuilder console = builder.newAppender("Stdout", "CONSOLE")
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
        builder.add(console);
        builder.add(builder.newLogger("com", Level.DEBUG)
                .add(builder.newAppenderRef("Stdout"))
                .addAttribute("additivity", false));
        builder.add(builder.newRootLogger(Level.ERROR)
                .add(builder.newAppenderRef("Stdout")));
        Configurator.initialize(builder.build());
        LogPrinter logPrinter = new LogPrinter();
        logPrinter.printlog();
    }
}
