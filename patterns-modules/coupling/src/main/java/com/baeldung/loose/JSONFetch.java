package com.baeldung.loose;

import java.util.ArrayList;
import java.util.List;

public class JSONFetch implements FetchMetadata{
    @Override
    public List<Object> fetchMetadata() {
        System.out.println("Fetching some json data");
        return new ArrayList<>();
    }
}
