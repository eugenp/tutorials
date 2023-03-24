package com.baeldung.loose;

import java.util.ArrayList;
import java.util.List;

public class XMLFetch implements FetchMetadata {
    @Override
    public List<Object> fetchMetadata() {
        List<Object> metadata = new ArrayList<>();
        // Do some stuff
        return metadata;
    }
}
