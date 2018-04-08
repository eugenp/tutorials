package com.baeldung.stream;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.stream.StreamTransformer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IgniteStream {

    public static void main(String[] args) throws Exception {
        // Mark this cluster member as client.
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start()) {
            IgniteCache<String, Long> stmCache = ignite.getOrCreateCache(CacheConfig.wordCache());

            // Create a streamer to stream words into the cache.
            try (IgniteDataStreamer<String, Long> stmr = ignite.dataStreamer(stmCache.getName())) {
                // Allow data updates.
                stmr.allowOverwrite(true);

                // Configure data transformation to count instances of the same word.
                stmr.receiver(StreamTransformer.from((e, arg) -> {
                    // Get current count.
                    Long val = e.getValue();

                    // Increment current count by 1.
                    e.setValue(val == null ? 1L : val + 1);

                    return null;
                }));

                // Stream words from "alice-in-wonderland" book.
                while (true) {
                    Path path = Paths.get(IgniteStream.class.getResource("employees.txt").toURI());

                    // Read words from a text file.
                    try (Stream<String> lines = Files.lines(path)) {
                        lines.forEach(line -> {
                            Stream<String> words = Stream.of(line.split(" "));

                            // Stream words into Ignite streamer.
                            words.forEach(word -> {
                                if (!word.trim().isEmpty())
                                    stmr.addData(word, 1L);
                            });
                        });
                    }
                }
            }
        }
    }
}
