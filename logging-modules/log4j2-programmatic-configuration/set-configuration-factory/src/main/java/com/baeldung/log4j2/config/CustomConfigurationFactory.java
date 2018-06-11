package com.baeldung.log4j2.config;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class CustomConfigurationFactory extends ConfigurationFactory {

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE");
        LayoutComponentBuilder layout = builder.newLayout("PatternLayout")
            .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");
        appenderBuilder.add(layout);
        FilterComponentBuilder filter = builder.newFilter("MarkerFilter", Filter.Result.ACCEPT, Filter.Result.DENY);
        filter.addAttribute("marker", "FLOW");
        appenderBuilder.add(filter);
        builder.add(appenderBuilder);
        LoggerComponentBuilder logger = builder.newLogger("com", Level.DEBUG);
        logger.add(builder.newAppenderRef("Stdout"));
        logger.addAttribute("additivity", false);
        builder.add(logger);
        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ERROR);
        rootLogger.add(builder.newAppenderRef("Stdout"));
        rootLogger.addAttribute("additivity", false);
        builder.add(rootLogger);
        return builder.build();
    }

    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] { "*" };
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

}
