package com.baeldung.dialect;

import java.util.HashSet;
import java.util.Set;

import com.baeldung.processor.NameProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class CustomDialect extends AbstractDialect {

    @Override
    public String getPrefix() {
        return "custom";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new NameProcessor());
        return processors;
    }

}
