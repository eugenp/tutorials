package com.baeldung.stream;

import java.util.stream.Stream;

public class StreamApi {

    public String getLastElementUsingReduce(Stream<String> stream) {
        return stream.reduce((first, second) -> second).orElse(null);
    }
    
    public String getLastElementUsingSkip(Stream<String> stream, long count) {
        return stream.skip(count - 1).findFirst().orElse(null);
    }

}
