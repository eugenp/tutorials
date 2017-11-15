package com.baeldung.beaninjectiontypes.model.engine;

public class EngineImplV8 implements Engine{
    private String engineVersion;

    public EngineImplV8() {
        this.engineVersion = "V8";
    }

    public String getEngineVersion() {
        return engineVersion;
    }
}