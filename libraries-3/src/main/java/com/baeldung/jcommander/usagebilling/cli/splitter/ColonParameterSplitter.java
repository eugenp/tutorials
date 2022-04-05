package com.baeldung.jcommander.usagebilling.cli.splitter;

import com.beust.jcommander.converters.IParameterSplitter;

import java.util.List;

import static java.util.Arrays.asList;

public class ColonParameterSplitter implements IParameterSplitter {

    @Override
    public List<String> split(String value) {
        return asList(value.split(":"));
    }
}
