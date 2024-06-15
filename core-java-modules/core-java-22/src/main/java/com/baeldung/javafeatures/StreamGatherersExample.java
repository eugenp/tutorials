package com.baeldung.javafeatures;

import java.util.List;
import java.util.stream.Gatherers;

public class StreamGatherersExample {
    public List<List<String>> gatherIntoWindows(List<String> countries) {
        List<List<String>> windows = countries.stream()
          .gather(Gatherers.windowSliding(3))
          .toList();
        return windows;
    }

}
