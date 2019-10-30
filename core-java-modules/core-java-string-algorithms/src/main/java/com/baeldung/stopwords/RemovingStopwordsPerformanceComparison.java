package com.baeldung.stopwords;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Fork(value = 3, warmups = 1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RemovingStopwordsPerformanceComparison {

    private String data;
    
    private List<String> stopwords;

    private String stopwordsRegex;
    
    
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Setup
    public void setup() throws IOException {
        data = new String(Files.readAllBytes(Paths.get("src/main/resources/shakespeare-hamlet.txt")));
        data = data.toLowerCase();
        stopwords = Files.readAllLines(Paths.get("src/main/resources/english_stopwords.txt"));
        stopwordsRegex = stopwords.stream().collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
    }

    @Benchmark
    public String removeManually() {
        String[] allWords = data.split(" ");
        StringBuilder builder = new StringBuilder();
        for(String word:allWords) {
            if(! stopwords.contains(word)) {
                builder.append(word);
                builder.append(' ');
            }
        }
        return builder.toString().trim();
    }

    @Benchmark
    public String removeAll() {
        ArrayList<String> allWords = Stream.of(data.split(" "))
            .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        return allWords.stream().collect(Collectors.joining(" "));
    }

    @Benchmark
    public String replaceRegex() {
        return data.replaceAll(stopwordsRegex, "");
    }

}