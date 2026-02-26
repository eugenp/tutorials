package com.baeldung.cannotinstantiate;

import java.util.List;

public abstract class DataExporter {
    protected String filename;

    public abstract void export(List<String> data);

    public String getFilename() {
        return filename;
    }
}
