package com.baeldung.jcommander.usagebilling.cli.splitter;

import static java.util.Arrays.asList;

import java.util.List;

import com.beust.jcommander.converters.IParameterSplitter;

public class ColonParameterSplitter implements IParameterSplitter {

    @Override
    public List<String> split(String value) {
        return asList(value.split(":"));
    }
}
