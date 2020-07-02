package com.baeldung.logging.log4j2.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

@Plugin(name = "DockerPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"docker", "container"})
public class DockerPatternConverter extends LogEventPatternConverter {

    private DockerPatternConverter(String[] options) {
        super("Docker", "docker");
    }

    public static DockerPatternConverter newInstance(String[] options) {
        return new DockerPatternConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        toAppendTo.append(dockerContainer());
    }

    private String dockerContainer() {
        //get docker container ID inside which application is running here
        return "container-1";
    }
}
