package com.baeldung.beaninjectiontypes.beans;

import com.baeldung.beaninjectiontypes.beans.api.Processor;

public class ProcessorImpl implements Processor {

    private String name;

    public ProcessorImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
